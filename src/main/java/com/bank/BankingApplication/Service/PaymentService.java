package com.bank.BankingApplication.Service;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
    public class PaymentService {

        private final String KEY_ID = "rzp_test_yourId";
        private final String KEY_SECRET = "yourSecret";

        public String createOrder(double amount) throws RazorpayException {
            RazorpayClient client = new RazorpayClient(KEY_ID, KEY_SECRET);

            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount", amount * 100); // Amount in paise
            orderRequest.put("currency", "INR");
            orderRequest.put("receipt", "txn_123456");

            Order order = client.orders.create(orderRequest);
            return order.toString();
        }
    }

