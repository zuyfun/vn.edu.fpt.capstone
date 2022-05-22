package vn.edu.fpt.capstone.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import vn.edu.fpt.capstone.common.Message;
import vn.edu.fpt.capstone.dto.PhuongXaDto;
import vn.edu.fpt.capstone.dto.ResponseObject;
import vn.edu.fpt.capstone.service.PhuongXaService;
import vn.edu.fpt.capstone.service.QuanHuyenService;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = 3600)
public class PhuongXaController {
	private static final Logger LOGGER = LoggerFactory.getLogger(PhuongXaController.class.getName());
	@Autowired
	private PhuongXaService phuongXaService;
	@Autowired
	private QuanHuyenService quanHuyenService;
	@GetMapping(value = "/phuongxa/{id}")
	public ResponseEntity<ResponseObject> getById(@PathVariable String id) {
		ResponseObject responseObject = new ResponseObject();
		try {
			Long lId = Long.valueOf(id);
			if (phuongXaService.isExist(lId)) {
				PhuongXaDto phuongXaDto = phuongXaService.findById(lId);
				responseObject.setResults(phuongXaDto);
				responseObject.setCode("200");
				responseObject.setMessage(Message.OK);
				return new ResponseEntity<>(responseObject, HttpStatus.OK);
			} else {
				responseObject.setCode("404");
				responseObject.setMessage(Message.NOT_FOUND);
				return new ResponseEntity<>(responseObject, HttpStatus.NOT_FOUND);
			}
		} catch (NumberFormatException e) {
			LOGGER.error(e.toString());
			responseObject.setCode("404");
			responseObject.setMessage(Message.NOT_FOUND);
			return new ResponseEntity<>(responseObject, HttpStatus.NOT_FOUND);
		} catch (Exception ex) {
			LOGGER.error(ex.toString());
			responseObject.setCode("500");
			responseObject.setMessage(Message.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/phuongxa")
	public ResponseEntity<ResponseObject> getAll() {
		ResponseObject responseObject = new ResponseObject();
		try {
			List<PhuongXaDto> phuongXaDtos = phuongXaService.findAll();
			if (phuongXaDtos == null || phuongXaDtos.isEmpty()) {
				responseObject.setCode("404");
				responseObject.setMessage(Message.NOT_FOUND);
				return new ResponseEntity<>(responseObject, HttpStatus.NOT_FOUND);
			}
			responseObject.setResults(phuongXaDtos);
			responseObject.setCode("200");
			responseObject.setMessage(Message.OK);
			return new ResponseEntity<>(responseObject, HttpStatus.OK);
		} catch (Exception ex) {
			LOGGER.error(ex.toString());
			responseObject.setCode("500");
			responseObject.setMessage(Message.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@ResponseBody
	@GetMapping(value = "/phuongxa/quanhuyen/{id}")
	public ResponseEntity<ResponseObject> getAllByQuanHuyenId(@PathVariable String id) {
		ResponseObject responseObject = new ResponseObject();
		try {
			Long lId = Long.valueOf(id);
			if (quanHuyenService.isExist(lId)) {
				List<PhuongXaDto> phuongXaDtos = phuongXaService.findAllByMaQh(lId);
				responseObject.setResults(phuongXaDtos);
				responseObject.setCode("200");
				responseObject.setMessage(Message.OK);
				return new ResponseEntity<>(responseObject, HttpStatus.OK);
			} else {
				responseObject.setCode("404");
				responseObject.setMessage(Message.NOT_FOUND);
				return new ResponseEntity<>(responseObject, HttpStatus.NOT_FOUND);
			}
		} catch (NumberFormatException e) {
			LOGGER.error(e.toString());
			responseObject.setCode("404");
			responseObject.setMessage(Message.NOT_FOUND);
			return new ResponseEntity<>(responseObject, HttpStatus.NOT_FOUND);
		} catch (Exception ex) {
			LOGGER.error(ex.toString());
			responseObject.setCode("500");
			responseObject.setMessage(Message.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
