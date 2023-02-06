package com.synergy.transaction.service.impl;

import com.synergy.transaction.config.BookingDuration;
import com.synergy.transaction.dto.PostBookingDto;
import com.synergy.transaction.entity.*;
import com.synergy.transaction.repository.*;
import com.synergy.transaction.service.TransactionService;
import com.synergy.transaction.util.RandomGenerator;
import com.synergy.transaction.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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

        Booking bookingSaved = bookingRepository.save(booking);

        // insert booking instance to transaction class
        transaction.setBooking(bookingSaved);

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

        formattedResponse.put("bookingId", bookingSaved.getBookingId());
        formattedResponse.put("bookingCode", bookingSaved.getBookingCode());
        formattedResponse.put("name", bookingSaved.getName());
        formattedResponse.put("gender", bookingSaved.getGender());
        formattedResponse.put("job", bookingSaved.getJob());
        formattedResponse.put("phoneNumber", bookingSaved.getPhoneNumber());
        formattedResponse.put("profileId", bookingSaved.getProfile().getId());
        formattedResponse.put("roomId", bookingSaved.getRoom().getId());

        return res.resSuccess(formattedResponse, "success", 200);
    }
}
