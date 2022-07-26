package com.xhu.reggie.dto;

import com.xhu.reggie.entity.Setmeal;
import com.xhu.reggie.entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
