package com.synergy.transaction.service.impl;

import com.synergy.transaction.config.BookingDuration;
import com.synergy.transaction.dto.PostBookingDto;
import com.synergy.transaction.entity.*;
import com.synergy.transaction.repository.*;
import com.synergy.transaction.service.TransactionService;
import com.synergy.transaction.util.RandomGenerator;
import com.synergy.transaction.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Map<String, Object> bookKost(Long profileId, Long roomId, PostBookingDto postBookingDto) {

        Optional<Profile> user = profileRepository.findById(profileId);
        Optional<Room> room = roomRepository.findById(roomId);


        if (!user.isPresent()) {
            return res.notFoundError("user doesn't exist");
        }

        if (!room.isPresent()) {
            return res.notFoundError("room doesn't exist");
        }

        Booking booking = new Booking();
        Transaction transaction = new Transaction();

        // generate booking code and insert to booking table
        booking.setBookingCode(RandomGenerator.getBookCode(profileId));

        // get user detail and insert to booking table
        booking.setName(postBookingDto.getName());
        booking.setGender(postBookingDto.getGender());
        booking.setJob(postBookingDto.getJob());
        booking.setPhoneNumber(postBookingDto.getPhoneNumber());

        // attach user and room that relation each other in booking table
        booking.setProfile(user.get());
        booking.setRoom(room.get());

        Booking bookingSaved = bookingRepository.save(booking);

        // insert booking instance to transaction class
        transaction.setBooking(booking);

        // generate invoice code and insert to transaction table
        transaction.setInvoiceCode(RandomGenerator.getTransactionCode(profileId));

        Optional<Price> price = priceRepository.findById(postBookingDto.getPriceId());

        if (price.isPresent()) {
            return res.notFoundError("price doesn't exist for this room");
        }

        // set transaction detail
        transaction.setPaymentMethod("BANK");
        transaction.setCheckIn(postBookingDto.getCheckIn());
        transaction.setCheckOut(postBookingDto
                .getCheckIn()
                .plusDays(
                        BookingDuration.getDuration(
                                price.get().getDurationType()
                        )
                ));
        transaction.setDeadlinePayment(booking.getCreatedAt().plusDays(1));
        transaction.setStatus("POSTED");

        transactionRepository.save(transaction);

        Map<String, Object> res = new HashMap<>();
        res.put("data", bookingSaved);

        return res;
    }
}
