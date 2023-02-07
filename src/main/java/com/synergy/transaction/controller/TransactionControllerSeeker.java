package com.synergy.transaction.controller;

import com.synergy.transaction.dto.PostBookingDto;
import com.synergy.transaction.dto.UploadProofOfPayment;
import com.synergy.transaction.repository.BookingRepository;
import com.synergy.transaction.service.impl.TransactionServiceImpl;
import com.synergy.transaction.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/seeker/transactions")
@CrossOrigin("*")
public class TransactionControllerSeeker {
    @Autowired
    private TransactionServiceImpl transactionServiceImpl;

    @Autowired
    private Response res;

    @Autowired
    private BookingRepository bookingRepository;

    @GetMapping("/list/{profileId}")
    public ResponseEntity<Map> getTransactionListByIdSeeker(
            @PathVariable Long profileId,
            @RequestParam(required = true) Integer page,
            @RequestParam(required = true) Integer size) {
        Pageable show_data = PageRequest.of(page, size);
        Page<Map<String, Object>> list = null;
        list = bookingRepository.getAllTransactionByIdSeeker(profileId, show_data);

        return new ResponseEntity<>(res.success(list, "success get list kost!", 200), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{transactionId}")
    public ResponseEntity<Map<String, Object>> deleteTransactionBySeeker(@PathVariable("transactionId") Long transactionId) {
        boolean deleteTransaction = transactionServiceImpl.softDeleteTransaction(transactionId);

        if (!deleteTransaction) {
            return res.clientError("transaction doesn't exist");
        }
        return res.resSuccess(1, "success", 200);
    }

    @PostMapping(value = "/user/{profileId}/room/{roomId}")
    public ResponseEntity<Map<String, Object>> createBookingRoom(
            @PathVariable("profileId") Long profileId,
            @PathVariable("roomId") Long roomId,
            @ModelAttribute @Valid PostBookingDto postBookingDto
    ) {
        try {
            return transactionServiceImpl.bookKost(profileId, roomId, postBookingDto);
        } catch (Exception e) {
            return res.internalServerError(e.getMessage());
        }
    }

    @PutMapping(value = "/transaction/{transactionId}")
    public ResponseEntity<Map<String, Object>> uploadTransactionSpoofImage(
            @PathVariable("transactionId") Long transactionId,
            @ModelAttribute @Valid UploadProofOfPayment uploadProofOfPayment
    ) {
        try {
            return transactionServiceImpl.uploadProofOfPayment(transactionId, uploadProofOfPayment);
        } catch (Exception e) {
            return res.internalServerError(e.getMessage());
        }
    }
}
