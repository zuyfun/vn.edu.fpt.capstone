package vn.edu.fpt.capstone.service;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;

import org.springframework.stereotype.Service;

@Service
public interface MailService {
	void sendMailVerifyCode(String email, String username, String code) throws MessagingException, UnsupportedEncodingException;
}