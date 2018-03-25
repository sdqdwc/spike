package com.mw.spike.service;

import java.util.List;

import com.mw.spike.dao.GoodsDao;
import com.mw.spike.domain.SpikeGoods;
import com.mw.spike.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class GoodsService {
	
	@Autowired
	GoodsDao goodsDao;
	
	public List<GoodsVo> listGoodsVo(){
		return goodsDao.listGoodsVo();
	}

	public GoodsVo getGoodsVoByGoodsId(long goodsId) {
		return goodsDao.getGoodsVoByGoodsId(goodsId);
	}

	/**
	 * 减库存
	 * @param goods
	 */
	public boolean reduceStock(GoodsVo goods) {
		SpikeGoods spikeGoods = new SpikeGoods();
		spikeGoods.setGoodsId(goods.getId());
		int ret = goodsDao.reduceStock(spikeGoods);
		return ret > 0;
	}


	/**
	 * 重置库存
	 * @param goodsList
	 */
	public void resetStock(List<GoodsVo> goodsList) {
		for(GoodsVo goods : goodsList ) {
			SpikeGoods g = new SpikeGoods();
			g.setGoodsId(goods.getId());
			g.setStockCount(goods.getStockCount());
			goodsDao.resetStock(g);
		}
	}

	
	
}
