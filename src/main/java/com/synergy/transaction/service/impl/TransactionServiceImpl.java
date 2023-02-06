package com.synergy.transaction.service.impl;

import com.synergy.transaction.config.BookingDuration;
import com.synergy.transaction.config.CloudFolder;
import com.synergy.transaction.dto.PostBookingDto;
import com.synergy.transaction.dto.UploadProofOfPayment;
import com.synergy.transaction.entity.*;
import com.synergy.transaction.repository.*;
import com.synergy.transaction.service.TransactionService;
import com.synergy.transaction.util.RandomGenerator;
import com.synergy.transaction.util.Response;
import com.synergy.transaction.util.UploadFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    ProfileRepository profileRepository;

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    PriceRepository priceRepository;

    @Autowired
    Response res;

    @Autowired
    UploadFile uploadFile;

    @Override
    public ResponseEntity<Map<String, Object>> bookKost(Long profileId, Long roomId, PostBookingDto postBookingDto) {

        Optional<Profile> user = profileRepository.findById(profileId);
        Optional<Room> room = roomRepository.findById(roomId);
        Optional<Price> price = priceRepository.findById(postBookingDto.getPrice_id());

        if (!user.isPresent()) {
            return res.notFoundError("user doesn't exist");
        }

        if (!room.isPresent()) {
            return res.notFoundError("room doesn't exist");
        }

        if (!price.isPresent()) {
            return res.notFoundError("price doesn't exist for this room");
        }

        Booking booking = new Booking();
        Transaction transaction = new Transaction();

        // generate booking code and insert to booking table
        booking.setBookingCode(RandomGenerator.getBookCode(profileId));

        // get user detail and insert to booking table
        booking.setName(postBookingDto.getName());
        booking.setGender(postBookingDto.getGender());
        booking.setJob(postBookingDto.getJob());
        booking.setPhoneNumber(postBookingDto.getPhone_number());

        // attach user and room that relation each other in booking table
        booking.setProfile(user.get());
        booking.setRoom(room.get());

        // save booking detail
        bookingRepository.save(booking);

        // insert booking instance to transaction class
        transaction.setBooking(booking);

        // generate invoice code and insert to transaction table
        transaction.setInvoiceCode(RandomGenerator.getTransactionCode(profileId));

        // set transaction detail
        transaction.setPaymentMethod("BANK");
        transaction.setCheckIn(postBookingDto.getCheck_in());
        transaction.setCheckOut(postBookingDto
                .getCheck_in()
                .plusDays(
                        BookingDuration.getDuration(
                                price.get().getDurationType()
                        )
                ));
        transaction.setDeadlinePayment(booking.getCreatedAt().plusDays(1));
        transaction.setStatus("POSTED");

        // save transaction detail
        transactionRepository.save(transaction);

        Map<String, Object> formattedResponse = new HashMap<>();

        formattedResponse.put("bookingId", booking.getBookingId());
        formattedResponse.put("bookingCode", booking.getBookingCode());
        formattedResponse.put("name", booking.getName());
        formattedResponse.put("gender", booking.getGender());
        formattedResponse.put("job", booking.getJob());
        formattedResponse.put("phoneNumber", booking.getPhoneNumber());
        formattedResponse.put("profileId", booking.getProfile().getId());
        formattedResponse.put("roomId", booking.getRoom().getId());

        return res.resSuccess(formattedResponse, "success", 200);
    }

    @Override
    public ResponseEntity<Map<String, Object>> uploadProofOfPayment(
            Long transactionId,
            UploadProofOfPayment uploadProofOfPayment
    ) throws IOException {
        Optional<Transaction> transaction = transactionRepository.findById(transactionId);

        if (!transaction.isPresent()) {
            return res.notFoundError("transaction doesn't exist");
        }

        // upload image
        String image = uploadFile
                .UploadSingleFile(uploadProofOfPayment
                        .getFile(), CloudFolder.TRANSACTION_FOLDER);

        transaction.get().setProofOfPayment(image);
        transaction.get().setDeadlinePayment(null);
        transaction.get().setStatus("REVIEWED");

        // save updated data
        transactionRepository.save(transaction.get());

        Map<String, Object> formattedResponse = new HashMap<>();
        formattedResponse.put("transactionId", transaction.get().getTransactionId());
        formattedResponse.put("invoiceCode", transaction.get().getInvoiceCode());
        formattedResponse.put("status", transaction.get().getStatus());

        return res.resSuccess(formattedResponse, "success", 200);
    }
}
