package me.controller;

import me.dao.Mysql;
import me.model.GeoInfo;

import org.nutz.dao.Dao;
import org.nutz.dao.impl.NutDao;
import org.nutz.mvc.adaptor.PairAdaptor;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Encoding;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

public class GeoModule {

	/**
	 * 接收所在位置地理数据
	 * 注意: rid是Room表中id的外键
	 * 
	 * @param roomId 房间号
	 * @param lon 经度
	 * @param lat 维度
	 * @return 成功返回true, 失败返回false
	 */
	@Encoding(input = "UTF-8", output = "UTF-8")
	@At("/geo/uploadGeoData")
	@Ok("raw:html")	
	@AdaptBy(type=PairAdaptor.class)
	public boolean uploadGeoData(@Param("roomId") int roomId,@Param("lon")  double lon, @Param("lat") double lat) {
		if(roomId==0 || lon==0 || lat==0){		//如果客户端没用post参数上来
			return false;
		}
		
		GeoInfo geoInfo = new GeoInfo();
		geoInfo.setLat(lat);
		geoInfo.setLon(lon);
		geoInfo.setRid(roomId);			
		try {
			Dao dao = new NutDao(Mysql.sds());
			if(dao.insert(geoInfo)==null){
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
}
