package vn.edu.fpt.capstone.dto;

import lombok.Data;

@Data
public class FavoriteDto extends BaseDto{
    private Long id;
    private Long userId;
    private Long roomId;
}
