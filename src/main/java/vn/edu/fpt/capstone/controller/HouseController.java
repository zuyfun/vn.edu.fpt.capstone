package vn.edu.fpt.capstone.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import vn.edu.fpt.capstone.common.Message;
import vn.edu.fpt.capstone.dto.HouseDto;
import vn.edu.fpt.capstone.dto.ResponseObject;
import vn.edu.fpt.capstone.service.AddressService;
import vn.edu.fpt.capstone.service.HouseService;
import vn.edu.fpt.capstone.service.TypeOfRentalService;
import vn.edu.fpt.capstone.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = 3600)
public class HouseController {
	private static final Logger LOGGER = LoggerFactory.getLogger(HouseController.class.getName());

	@Autowired
	HouseService houseService;
	@Autowired
	AddressService addressService;
	@Autowired
	TypeOfRentalService typeOfRentalService;
	@Autowired
	UserService userService;

	@GetMapping(value = "/boardingHouse/{id}")
	public ResponseEntity<ResponseObject> getById(@PathVariable String id) {
		ResponseObject responseObject = new ResponseObject();
		try {
			Long lId = Long.valueOf(id);
			if (houseService.isExist(lId)) {
				HouseDto houseDto = houseService.findById(lId);
				responseObject.setResults(houseDto);
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

	@GetMapping(value = "/boardingHouse")
	public ResponseEntity<ResponseObject> getAll() {
		ResponseObject responseObject = new ResponseObject();
		try {
			List<HouseDto> houseDtos = houseService.findAll();
			if (houseDtos == null || houseDtos.isEmpty()) {
				responseObject.setCode("404");
				responseObject.setMessage(Message.NOT_FOUND);
				return new ResponseEntity<>(responseObject, HttpStatus.NOT_FOUND);
			}
			responseObject.setResults(houseDtos);
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

	@PostMapping(value = "/boardingHouse")
	public ResponseEntity<ResponseObject> postBoardingHouse(@RequestBody HouseDto houseDto) {
		ResponseObject response = new ResponseObject();
		try {
			if (houseDto.getId() != null
					|| (houseDto.getUserId() == null || !userService.checkIdExist(houseDto.getUserId()))
					|| (houseDto.getAddressId() == null || !addressService.isExist(houseDto.getAddressId()))
					|| (houseDto.getTypeOfRentalId() == null
							|| !typeOfRentalService.isExist(houseDto.getTypeOfRentalId()))) {
				response.setCode("406");
				response.setMessage(Message.NOT_ACCEPTABLE);
				return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
			}

			HouseDto houseDto2 = houseService.createHouse(houseDto);
			if (houseDto2 == null) {
				response.setCode("500");
				response.setMessage(Message.INTERNAL_SERVER_ERROR);
				return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.setCode("200");
			response.setMessage(Message.OK);
			response.setResults(houseDto2);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error(e.toString());
			response.setCode("500");
			response.setMessage(Message.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping(value = "/boardingHouse")
	public ResponseEntity<ResponseObject> putBoardingHouse(@RequestBody HouseDto houseDto) {
		ResponseObject response = new ResponseObject();
		try {
			if (houseDto.getId() == null || !houseService.isExist(houseDto.getId())) {
				response.setCode("404");
				response.setMessage(Message.NOT_FOUND);
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}
			if ((houseDto.getUserId() == null || !userService.checkIdExist(houseDto.getUserId()))
					|| (houseDto.getAddressId() == null || !addressService.isExist(houseDto.getAddressId()))
					|| (houseDto.getTypeOfRentalId() == null
							|| !typeOfRentalService.isExist(houseDto.getTypeOfRentalId()))) {
				response.setCode("406");
				response.setMessage(Message.NOT_ACCEPTABLE);
				return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
			}
			HouseDto houseDto2 = houseService.updateHouse(houseDto);
			response.setCode("200");
			response.setMessage(Message.OK);
			response.setResults(houseDto2);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error(e.toString());
			response.setCode("500");
			response.setMessage(Message.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping(value = "/boardingHouse/{id}")
	public ResponseEntity<ResponseObject> deleteBoardingHouse(@PathVariable String id) {
		ResponseObject response = new ResponseObject();
		try {
			if (id == null || id.isEmpty() || !houseService.isExist(Long.valueOf(id))) {
				response.setCode("404");
				response.setMessage(Message.NOT_FOUND);
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}
			response.setCode("200");
			response.setMessage(Message.OK);
			houseService.removeHouse(Long.valueOf(id));
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (NumberFormatException ex) {
			LOGGER.error(ex.toString());
			response.setCode("404");
			response.setMessage(Message.NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			LOGGER.error(e.toString());
			response.setCode("500");
			response.setMessage(Message.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
