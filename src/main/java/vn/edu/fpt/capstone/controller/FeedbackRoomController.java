package vn.edu.fpt.capstone.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.capstone.dto.FeedbackRoomDto;
import vn.edu.fpt.capstone.dto.ResponseObject;
import vn.edu.fpt.capstone.service.FeedbackRoomService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class FeedbackRoomController {
    private static final Logger LOGGER = LoggerFactory.getLogger(FeedbackRoomController.class.getName());

    @Autowired
    FeedbackRoomService feedbackRoomService;

    @GetMapping(value = "/feedbackRoom/{id}")
    public ResponseEntity<ResponseObject> getById(@PathVariable String id) {
        ResponseObject responseObject = new ResponseObject();
        try {
            Long lId = Long.valueOf(id);
            if (feedbackRoomService.isExist(lId)) {
                FeedbackRoomDto feedbackRoomDto = feedbackRoomService.findById(lId);
                responseObject.setResults(feedbackRoomDto);
                responseObject.setCode("1001");
                responseObject.setMessage("Successfully");
                return new ResponseEntity<>(responseObject, HttpStatus.OK);
            } else {
                responseObject.setCode("1002");
                responseObject.setMessage("Not found");
                return new ResponseEntity<>(responseObject, HttpStatus.NOT_FOUND);
            }
        } catch (NumberFormatException e) {
            LOGGER.error(e.toString());
            responseObject.setCode("1002");
            responseObject.setMessage("Not found");
            return new ResponseEntity<>(responseObject, HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            LOGGER.error(ex.toString());
            responseObject.setCode("1003");
            responseObject.setMessage("Internal Server Error");
            return new ResponseEntity<>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/feedbackRoom")
    public ResponseEntity<ResponseObject> getAll() {
        ResponseObject responseObject = new ResponseObject();
        try {
            List<FeedbackRoomDto> feedbackRoomDtos = feedbackRoomService.findAll();
            if (feedbackRoomDtos == null || feedbackRoomDtos.isEmpty()) {
                responseObject.setCode("1002");
                responseObject.setMessage("No data");
                return new ResponseEntity<>(responseObject, HttpStatus.NOT_FOUND);
            }
            responseObject.setResults(feedbackRoomDtos);
            responseObject.setCode("1001");
            responseObject.setMessage("Successfully");
            return new ResponseEntity<>(responseObject, HttpStatus.OK);
        } catch (Exception ex) {
            LOGGER.error(ex.toString());
            responseObject.setCode("1003");
            responseObject.setMessage("Internal Server Error");
            return new ResponseEntity<>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/feedbackRoom")
    public ResponseEntity<ResponseObject> postFeedbackRoom(@RequestBody FeedbackRoomDto feedbackRoomDto) {
        ResponseObject response = new ResponseObject();
        try {
            if (feedbackRoomDto.getId() != null) {
                response.setMessage("Invalid data");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            response.setMessage("Create successfully");
            feedbackRoomService.createFeedbackRoom(feedbackRoomDto);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error(e.toString());
            response.setCode("1001");
            response.setMessage("Failed");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/feedbackRoom")
    public ResponseEntity<ResponseObject> putFeedbackRoom(@RequestBody FeedbackRoomDto feedbackRoomDto) {
        ResponseObject response = new ResponseObject();
        try {
            if (feedbackRoomDto.getId() == null || !feedbackRoomService.isExist(feedbackRoomDto.getId())) {
                response.setCode("1001");
                response.setMessage("Invalid data");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            response.setCode("1000");
            response.setMessage("Update successfully");
            feedbackRoomService.updateFeedbackRoom(feedbackRoomDto);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error(e.toString());
            response.setCode("1001");
            response.setMessage("Failed");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/feedbackRoom/{id}")
    public ResponseEntity<ResponseObject> deleteFeedbackRoom(@PathVariable String id) {
        ResponseObject response = new ResponseObject();
        try {
            if (id == null || id.isEmpty() || !feedbackRoomService.isExist(Long.valueOf(id))) {
                response.setCode("1001");
                response.setMessage("Id is not exist");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            response.setCode("1000");
            response.setMessage("Delete successfully");
            feedbackRoomService.removeFeedbackRoom(Long.valueOf(id));
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NumberFormatException ex) {
            LOGGER.error(ex.toString());
            response.setCode("1001");
            response.setMessage("Id is not exist");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            LOGGER.error(e.toString());
            response.setCode("1001");
            response.setMessage("Failed");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}