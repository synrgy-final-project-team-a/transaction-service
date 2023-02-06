package com.synergy.transaction.service.impl;

import com.synergy.transaction.entity.Transaction;
import com.synergy.transaction.entity.enumeration.EStatus;
import com.synergy.transaction.repository.TransactionRepository;
import com.synergy.transaction.service.TransactionService;
import com.synergy.transaction.util.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.io.IOException;
import java.util.*;

@Service
public class TransactionServiceImpl implements TransactionService {
    private static Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);

    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    public Response res;

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
                                price.get().getDurationType())));
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
    public boolean deleteTransaction(Long profileId, Long transactionId) {
        return false;
    }

    @Override
    public Boolean approveTransaction(Long transactionId) {
        try {
            Transaction checkingData = transactionRepository.findByTransactionId(transactionId);
            if (checkingData == null) {
                return false;
            }
            checkingData.setStatus(EStatus.APPROVED.name());
            checkingData.setDeadlinePayment(null);
            checkingData.setInvoiceCode(
                    checkingData.getCreatedAt().getYear() + "/" + checkingData.getCreatedAt().getMonthValue() + "/"
                            + checkingData.getCreatedAt().getDayOfMonth() + "/" + checkingData.getTransactionId());
            Transaction done = transactionRepository.save(checkingData);
            return true;

            // @Override
            // public Page<Transaction>
            // getAllTransactionHistoryByIdSeekerWithPagination(Long seekerId, Pageable
            // pageable) {
            // return transactionRepository.findAllBySeekerId(seekerId, pageable);
            // }

        } catch (Exception e) {
            logger.error("Error approve transaction, {} " + e);
        }
        return false;
    }

    public ResponseEntity<Map<String, Object>> uploadProofOfPayment(
            Long transactionId,
            UploadProofOfPayment uploadProofOfPayment) throws IOException {
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

    @Override
    public ResponseEntity<Map<String, Object>> getTransactionHistoryByIdBooking(Long bookingId) {
        List<Map<String, Object>> data = bookingRepository.getTransactionById(bookingId);
        List<Map<String, Object>> booking = new ArrayList<>();

        for (Map<String, Object> response : data) {
            Map<String, Object> itemBooking = new HashMap<>();
            // Add field room
            itemBooking.put("booking_id", response.get("booking_id"));
            itemBooking.put("booking_code", response.get("booking_code"));
            itemBooking.put("kost_name", response.get("kost_name"));
            itemBooking.put("room_name", response.get("room_name"));
            itemBooking.put("address", response.get("address"));
            itemBooking.put("city", response.get("city"));
            itemBooking.put("province", response.get("province"));
            itemBooking.put("price", response.get("price"));
            itemBooking.put("duration_type", response.get("duration_type"));
            itemBooking.put("status", response.get("status"));
            itemBooking.put("date_payment", response.get("updated_at"));
            itemBooking.put("payment_method", response.get("payment_method"));
            itemBooking.put("profile_id", response.get("id"));
            itemBooking.put("bank_name", response.get("bank_name"));

            booking.add(itemBooking);
        }

        return booking.size() > 0 ? res.resSuccess(booking, "success", 200)
                : res.notFoundError("booking doesn't exist");

    }

    @Override
    public ResponseEntity<Map<String, Object>> getTransactionHistoryByIdBookingAdmin(Long bookingId) {
        List<Map<String, Object>> data = bookingRepository.getTransactionByIdAdmin(bookingId);
        List<Map<String, Object>> booking = new ArrayList<>();

        for (Map<String, Object> response : data) {
            Map<String, Object> itemBooking = new HashMap<>();
            // Add field room
            itemBooking.put("booking_id", response.get("booking_id"));
            itemBooking.put("booking_code", response.get("booking_code"));
            itemBooking.put("kost_name", response.get("kost_name"));
            itemBooking.put("room_name", response.get("room_name"));
            itemBooking.put("address", response.get("address"));
            itemBooking.put("city", response.get("city"));
            itemBooking.put("province", response.get("province"));
            itemBooking.put("price", response.get("price"));
            itemBooking.put("duration_type", response.get("duration_type"));
            itemBooking.put("status", response.get("status"));
            itemBooking.put("date_payment", response.get("updated_at"));
            itemBooking.put("payment_method", response.get("payment_method"));
            itemBooking.put("profile_id", response.get("id"));
            itemBooking.put("bank_name", response.get("bank_name"));

            booking.add(itemBooking);
        }

        return booking.size() > 0 ? res.resSuccess(booking, "success", 200)
                : res.notFoundError("booking doesn't exist");

    }

    @Override
    public Boolean rejectTransaction(Long transactionId) {
        try {
            Transaction checkingData = transactionRepository.findByTransactionId(transactionId);
            if (checkingData == null) {
                return false;
            }
            checkingData.setStatus(EStatus.CANCELLED.name());
            checkingData.setDeadlinePayment(null);
            Transaction done = transactionRepository.save(checkingData);
            return true;

        } catch (Exception e) {
            logger.error("Error approve transaction, {} " + e);
        }
        return false;
    }

    @Override
    public Boolean softDeleteTransaction(Long transactionId) {
        try {
            Transaction checkingData = transactionRepository.findByTransactionId(transactionId);
            if (checkingData == null) {
                return false;
            }
            checkingData.setStatus(EStatus.CANCELLED.name());
            checkingData.setDeadlinePayment(null);
            checkingData.setDeletedAt(new Timestamp(System.currentTimeMillis()).toLocalDateTime());
            Transaction done = transactionRepository.save(checkingData);
            return true;

        } catch (Exception e) {
            logger.error("Error approve transaction, {} " + e);
        }
        return false;
    }

    @Override
    public Map<String, List<Map<String, Object>>> getAllTransactionByIdTennant(Long profileId) {
        List<Map<String, Object>> data = transactionRepository.findAllTransactionTennant(profileId);
        Map<String, List<Map<String, Object>>> resp = new HashMap<>();
        List<Map<String, Object>> transaction = new ArrayList<>();

        for (Map<String, Object> response : data) {
            Map<String, Object> itemTransaction = new HashMap<>();

            // Add field room
            itemTransaction.put("price", response.get("price"));
            itemTransaction.put("booking_id", response.get("booking_id"));
            itemTransaction.put("status", response.get("status"));
            itemTransaction.put("kost_name", response.get("kost_name"));
            itemTransaction.put("address", response.get("address"));

            transaction.add(itemTransaction);
        }

        resp.put("transaction", transaction);
        return resp;
    }

    @Override
    public Map<String, List<Map<String, Object>>> getAllTransactionByIdAdmin() {
        List<Map<String, Object>> data = transactionRepository.findAllTransactionAdmin();
        Map<String, List<Map<String, Object>>> resp = new HashMap<>();
        List<Map<String, Object>> transaction = new ArrayList<>();

        for (Map<String, Object> response : data) {
            Map<String, Object> itemTransaction = new HashMap<>();

            // Add field room
            itemTransaction.put("price", response.get("price"));
            itemTransaction.put("booking_id", response.get("booking_id"));
            itemTransaction.put("status", response.get("status"));
            itemTransaction.put("kost_name", response.get("kost_name"));
            itemTransaction.put("address", response.get("address"));

            transaction.add(itemTransaction);
        }

        resp.put("transaction", transaction);
        return resp;
    }
}
