package com.mw.spike.dao;

import java.util.List;

import com.mw.spike.domain.SpikeGoods;
import com.mw.spike.vo.GoodsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;


@Mapper
public interface GoodsDao {
	
	@Select("select g.*,sg.stock_count, sg.start_date, sg.end_date, sg.spike_price " +
			"from spike_goods sg left join goods g on sg.goods_id = g.id")
	List<GoodsVo> listGoodsVo();

	@Select("select g.*,sg.stock_count, sg.start_date, sg.end_date,sg.spike_price " +
			"from spike_goods sg left join goods g on sg.goods_id = g.id where g.id = #{goodsId}")
	GoodsVo getGoodsVoByGoodsId(@Param("goodsId") long goodsId);

	@Update("update spike_goods set stock_count = stock_count - 1 where goods_id = #{goodsId} and stock_count > 0")
	int reduceStock(SpikeGoods spikeGoods);

	@Update("update spike_goods set stock_count = #{stockCount} where goods_id = #{goodsId}")
	int resetStock(SpikeGoods g);


}
