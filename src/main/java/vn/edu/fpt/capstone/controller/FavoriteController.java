package vn.edu.fpt.capstone.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import vn.edu.fpt.capstone.constant.Message;
import vn.edu.fpt.capstone.dto.FavoriteDto;
import vn.edu.fpt.capstone.dto.ResponseObject;
import vn.edu.fpt.capstone.service.FavoriteService;
import vn.edu.fpt.capstone.service.RoomService;
import vn.edu.fpt.capstone.service.UserService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = 3600)
public class FavoriteController {
	private static final Logger LOGGER = LoggerFactory.getLogger(FavoriteController.class.getName());

	@Autowired
	FavoriteService favoriteService;
	@Autowired
	UserService userService;
	@Autowired
	RoomService roomService;

	@GetMapping(value = "/favorite/{id}")
	public ResponseEntity<ResponseObject> getById(@PathVariable String id) {
		ResponseObject responseObject = new ResponseObject();
		try {
			Long lId = Long.valueOf(id);
			if (favoriteService.isExist(lId)) {
				FavoriteDto favoriteDto = favoriteService.findById(lId);
				responseObject.setResults(favoriteDto);
				responseObject.setCode("200");
				responseObject.setMessageCode(Message.OK);
				LOGGER.info("getById: {}",favoriteDto);
				return new ResponseEntity<>(responseObject, HttpStatus.OK);
			} else {
				responseObject.setCode("404");
				responseObject.setMessageCode(Message.NOT_FOUND);
				LOGGER.error("getById: {}","ID Favorite is not exist");
				return new ResponseEntity<>(responseObject, HttpStatus.NOT_FOUND);
			}
		} catch (NumberFormatException e) {
			LOGGER.error("getById: {}",e);
			responseObject.setCode("404");
			responseObject.setMessageCode(Message.NOT_FOUND);
			return new ResponseEntity<>(responseObject, HttpStatus.NOT_FOUND);
		} catch (Exception ex) {
			LOGGER.error("getById: {}",ex);
			responseObject.setCode("500");
			responseObject.setMessageCode(Message.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/favorite")
	public ResponseEntity<ResponseObject> getAll() {
		ResponseObject responseObject = new ResponseObject();
		try {
			List<FavoriteDto> favoriteDtos = favoriteService.findAll();
			if (favoriteDtos == null || favoriteDtos.isEmpty()) {
				responseObject.setResults(new ArrayList<>());
			} else {
				responseObject.setResults(favoriteDtos);
			}
			LOGGER.info("getAll: {}",favoriteDtos);
			responseObject.setCode("200");
			responseObject.setMessageCode(Message.OK);
			return new ResponseEntity<>(responseObject, HttpStatus.OK);
		} catch (Exception ex) {
			LOGGER.error("getAll: {}",ex);
			responseObject.setCode("500");
			responseObject.setMessageCode(Message.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/favorite")
	public ResponseEntity<ResponseObject> postFavorite(@RequestBody FavoriteDto favoriteDto) {
		ResponseObject response = new ResponseObject();
		try {
			if (favoriteDto.getId() != null
					|| (favoriteDto.getUserId() == null || !userService.checkIdExist(favoriteDto.getUserId()))
					|| (favoriteDto.getRoomId() == null || !roomService.isExist(favoriteDto.getRoomId()))) {
				response.setCode("406");
				response.setMessageCode(Message.NOT_ACCEPTABLE);
				LOGGER.error("postFavorite: {}","Wrong body format or ID User or ID Room is not exist");
				return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
			}
			FavoriteDto favoriteDto2 = favoriteService.createFavorite(favoriteDto);
			if (favoriteDto2 == null) {
				response.setCode("500");
				response.setMessageCode(Message.INTERNAL_SERVER_ERROR);
				return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.setCode("200");
			response.setMessageCode(Message.OK);
			response.setResults(favoriteDto2);
			LOGGER.info("postFavorite: {}",favoriteDto2);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("postFavorite: {}",e);
			response.setCode("500");
			response.setMessageCode(Message.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping(value = "/favorite")
	public ResponseEntity<ResponseObject> putFavorite(@RequestBody FavoriteDto favoriteDto) {
		ResponseObject response = new ResponseObject();
		try {
			if (favoriteDto.getId() == null || !favoriteService.isExist(favoriteDto.getId())) {
				LOGGER.error("putFavorite: {}","ID Favorite is not exist");
				response.setCode("404");
				response.setMessageCode(Message.NOT_FOUND);
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}
			if ((favoriteDto.getUserId() == null || !userService.checkIdExist(favoriteDto.getUserId()))
					|| (favoriteDto.getRoomId() == null || !roomService.isExist(favoriteDto.getRoomId()))) {
				response.setCode("406");
				response.setMessageCode(Message.NOT_ACCEPTABLE);
				LOGGER.error("putFavorite: {}","ID User or ID Room is not exist");
				return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
			}
			FavoriteDto favoriteDto2 = favoriteService.updateFavorite(favoriteDto);
			response.setCode("200");
			response.setMessageCode(Message.OK);
			response.setResults(favoriteDto2);
			LOGGER.info("putFavorite: {}",favoriteDto2);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("putFavorite: {}",e);
			response.setCode("500");
			response.setMessageCode(Message.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping(value = "/favorite/{id}")
	public ResponseEntity<ResponseObject> deleteFavorite(@PathVariable String id) {
		ResponseObject response = new ResponseObject();
		try {
			if (id == null || id.isEmpty() || !favoriteService.isExist(Long.valueOf(id))) {
				LOGGER.error("deleteFavorite: {}","ID Favorite is not exist");
				response.setCode("404");
				response.setMessageCode(Message.NOT_FOUND);
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}
			response.setCode("200");
			response.setMessageCode(Message.OK);
			favoriteService.removeFavorite(Long.valueOf(id));
			LOGGER.error("deleteFavorite: {}","DELETED");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (NumberFormatException ex) {
			LOGGER.error("deleteFavorite: {}",ex);
			response.setCode("404");
			response.setMessageCode(Message.NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			LOGGER.error("deleteFavorite: {}",e);
			response.setCode("500");
			response.setMessageCode(Message.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
