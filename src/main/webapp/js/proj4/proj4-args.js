
//[전지구 좌표계]
//WGS84 경위도
//EPSG:4326, EPSG:4166 (Korean 1995)
proj4.defs('EPSG:4326', '+proj=longlat +ellps=WGS84 +datum=WGS84 +no_defs');

//Bessel 1841 경위도
//EPSG:4004, EPSG:4162 (Korean 1985)
proj4.defs('EPSG:4004', '+proj=longlat +ellps=bessel +no_defs +towgs84=-115.80,474.99,674.11,1.16,-2.31,-1.63,6.43');

//GRS80 경위도
//EPSG:4019, EPSG:4737 (Korean 2000)
proj4.defs('EPSG:4019', '+proj=longlat +ellps=GRS80 +no_defs');

//Google Mercator, 구글지도/빙지도/야후지도/OSM 등 에서 사용중인 좌표계
//EPSG:900913(통칭), EPSG:3857(공식)
proj4.defs('EPSG:3857', '+proj=merc +a=6378137 +b=6378137 +lat_ts=0.0 +lon_0=0.0 +x_0=0.0 +y_0=0 +k=1.0 +units=m +nadgrids=@null +no_defs');


//[UTM]
//UTM52N (WGS84)
//EPSG:32652
proj4.defs('EPSG:32652', '+proj=utm +zone=52 +ellps=WGS84 +datum=WGS84 +units=m +no_defs');

//UTM51N (WGS84)
//EPSG:32651
proj4.defs('EPSG:32651', '+proj=utm +zone=51 +ellps=WGS84 +datum=WGS84 +units=m +no_defs');


//[보정안된 오래된 지리원 표준]
//동부원점(Bessel)
//EPSG:2096
proj4.defs('EPSG:2096', '+proj=tmerc +lat_0=38 +lon_0=129 +k=1 +x_0=200000 +y_0=500000 +ellps=bessel +units=m +no_defs +towgs84=-115.80,474.99,674.11,1.16,-2.31,-1.63,6.43');

//중부원점(Bessel)
//EPSG:2097
proj4.defs('EPSG:2097', '+proj=tmerc +lat_0=38 +lon_0=127 +k=1 +x_0=200000 +y_0=500000 +ellps=bessel +units=m +no_defs +towgs84=-115.80,474.99,674.11,1.16,-2.31,-1.63,6.43');

//서부원점(Bessel)
//EPSG:2098
proj4.defs('EPSG:2098', '+proj=tmerc +lat_0=38 +lon_0=125 +k=1 +x_0=200000 +y_0=500000 +ellps=bessel +units=m +no_defs +towgs84=-115.80,474.99,674.11,1.16,-2.31,-1.63,6.43');


//[오래된 지리원 표준]
//보정된 서부원점(Bessel) – KLIS에서 서부지역에 사용중
//EPSG:5173
proj4.defs('EPSG:5173', '+proj=tmerc +lat_0=38 +lon_0=125.0028902777778 +k=1 +x_0=200000 +y_0=500000 +ellps=bessel +units=m +no_defs +towgs84=-115.80,474.99,674.11,1.16,-2.31,-1.63,6.43');

//보정된 중부원점(Bessel) – KLIS에서 중부지역에 사용중
//EPSG:5174
proj4.defs('EPSG:5174', '+proj=tmerc +lat_0=38 +lon_0=127.0028902777778 +k=1 +x_0=200000 +y_0=500000 +ellps=bessel +units=m +no_defs +towgs84=-115.80,474.99,674.11,1.16,-2.31,-1.63,6.43');

//보정된 제주원점(Bessel) – KLIS에서 제주지역에 사용중
//EPSG:5175
proj4.defs('EPSG:5175', '+proj=tmerc +lat_0=38 +lon_0=127.0028902777778 +k=1 +x_0=200000 +y_0=550000 +ellps=bessel +units=m +no_defs  +towgs84=-115.80,474.99,674.11,1.16,-2.31,-1.63,6.43');

//보정된 동부원점(Bessel) – KLIS에서 동부지역에 사용중
//EPSG:5176
proj4.defs('EPSG:5176', '+proj=tmerc +lat_0=38 +lon_0=129.0028902777778 +k=1 +x_0=200000 +y_0=500000 +ellps=bessel +units=m +no_defs +towgs84=-115.80,474.99,674.11,1.16,-2.31,-1.63,6.43');

//보정된 동해(울릉)원점(Bessel) – KLIS에서 울릉지역에 사용중
//EPSG:5177
proj4.defs('EPSG:5177', '+proj=tmerc +lat_0=38 +lon_0=131.0028902777778 +k=1 +x_0=200000 +y_0=500000 +ellps=bessel +units=m +no_defs  +towgs84=-115.80,474.99,674.11,1.16,-2.31,-1.63,6.43');


//[KATEC 계열]
//UTM-K (Bessel) – 새주소지도에서 사용 중
//EPSG:5178
proj4.defs('EPSG:5178', '+proj=tmerc +lat_0=38 +lon_0=127.5 +k=0.9996 +x_0=1000000 +y_0=2000000 +ellps=bessel +units=m +no_defs +towgs84=-115.80,474.99,674.11,1.16,-2.31,-1.63,6.43');

//UTM-K (GRS80), 네이버지도에서 사용중인 좌표계
//EPSG:5179
proj4.defs('EPSG:5179', '+proj=tmerc +lat_0=38 +lon_0=127.5 +k=0.9996 +x_0=1000000 +y_0=2000000 +ellps=GRS80 +units=m +no_defs');


//[타원체 바꾼 지리원 표준]
//서부원점(GRS80)-falseY:50000
//EPSG:5180
proj4.defs('EPSG:5180', '+proj=tmerc +lat_0=38 +lon_0=125 +k=1 +x_0=200000 +y_0=500000 +ellps=GRS80 +units=m +no_defs');

//중부원점(GRS80)-falseY:50000, 다음지도에서 사용중인 좌표계
//EPSG:5181
proj4.defs('EPSG:5181', '+proj=tmerc +lat_0=38 +lon_0=127 +k=1 +x_0=200000 +y_0=500000 +ellps=GRS80 +units=m +no_defs');

//제주원점(GRS80)-falseY:55000
//EPSG:5181
proj4.defs('EPSG:5181', '+proj=tmerc +lat_0=38 +lon_0=127 +k=1 +x_0=200000 +y_0=550000 +ellps=GRS80 +units=m +no_defs');

//동부원점(GRS80)-falseY:50000
//EPSG:5183
proj4.defs('EPSG:5183', '+proj=tmerc +lat_0=38 +lon_0=129 +k=1 +x_0=200000 +y_0=500000 +ellps=GRS80 +units=m +no_defs');

//동해(울릉)원점(GRS80)-falseY:50000
//EPSG:5184
proj4.defs('EPSG:5184', '+proj=tmerc +lat_0=38 +lon_0=131 +k=1 +x_0=200000 +y_0=500000 +ellps=GRS80 +units=m +no_defs');



//[현재 국토지리정보원 표준]
//서부원점(GRS80)-falseY:60000
//EPSG:5185
proj4.defs('EPSG:5185', '+proj=tmerc +lat_0=38 +lon_0=125 +k=1 +x_0=200000 +y_0=600000 +ellps=GRS80 +units=m +no_defs');

//중부원점(GRS80)-falseY:60000
//EPSG:5186
proj4.defs('EPSG:5186', '+proj=tmerc +lat_0=38 +lon_0=127 +k=1 +x_0=200000 +y_0=600000 +ellps=GRS80 +units=m +no_defs');

//동부원점(GRS80)-falseY:60000
//EPSG:5187
proj4.defs('EPSG:5187', '+proj=tmerc +lat_0=38 +lon_0=129 +k=1 +x_0=200000 +y_0=600000 +ellps=GRS80 +units=m +no_defs');

//동해(울릉)원점(GRS80)-falseY:60000
//EPSG:5188
proj4.defs('EPSG:5188', '+proj=tmerc +lat_0=38 +lon_0=131 +k=1 +x_0=200000 +y_0=600000 +ellps=GRS80 +units=m +no_defs');
