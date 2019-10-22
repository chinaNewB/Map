package com.example.qimo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.overlayutil.PoiOverlay;
import com.baidu.mapapi.search.core.CityInfo;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiDetailSearchResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ListFragment extends Fragment {
    private View view;
    private ListView mListView;
    @SuppressLint("NewApi")

    private Button food,attractions,hotel,bank,shopping;

    private PoiSearch mPoiSearch = null;
    private String[] datas;
    private String[] cities;
    private String[] addresses;
    private String[] types;
    private Double[] overallRatings;
    private Double[] prices;
    private int[] commentNums;
    private String[] detailUrls;

    private List<Map<String, Object>> mData;


    private String typeofsearch = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        此处数据类型为View，所以直接返回inflater即可
        view = inflater.inflate(R.layout.list_fragment,container,false);

        if (view!=null){
            initView();
        }

        typeofsearch = "美食";
        searchInQuanzhou(typeofsearch);

        return view;
    }

    public void


    searchInQuanzhou(String typeofsearch){
        mPoiSearch = PoiSearch.newInstance();
        mPoiSearch.setOnGetPoiSearchResultListener(listener);
        mPoiSearch.searchInCity((new PoiCitySearchOption())
                .city("泉州")
                .keyword(typeofsearch)
                .scope(2)  //设置是否返回详细信息
                .pageNum(0));       //从第0页开始
    }
    public void initView(){
        food = (Button)view.findViewById(R.id.foods);
        attractions = (Button)view.findViewById(R.id.attractions);
        hotel = (Button)view.findViewById(R.id.hotels);
        bank = (Button)view.findViewById(R.id.banks);
        shopping = (Button)view.findViewById(R.id.shoppings);
        mListView = (ListView)view.findViewById(R.id.lists);


    }

    OnGetPoiSearchResultListener listener = new OnGetPoiSearchResultListener() {
        @Override
        public void onGetPoiResult(PoiResult result) {
            if (result == null
                    || result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
                Toast.makeText(getActivity(), "未找到结果", Toast.LENGTH_LONG)
                        .show();
            }
            if (result.error == SearchResult.ERRORNO.NO_ERROR) {
                List<PoiInfo> allPoi = result.getAllPoi();

                datas = new String[10];  //搜索结果这里我们先设置只显示10条
                cities = new String[10];
                addresses = new String[10];
                types = new String[10];
                overallRatings = new Double[10];
                prices = new Double[10];
                commentNums = new int[10];
                detailUrls = new String[10];

                for (int i = 0; i < 10; i++) {
                    datas[i] = allPoi.get(i).name;      //获取的所有poi相关名字
                    cities[i] = allPoi.get(i).city;        //对应的城市
                    addresses[i] = allPoi.get(i).address;  //对应的详细地址
                    types[i] = allPoi.get(i).getPoiDetailInfo().getTag(); //类型，如：中餐厅
                    overallRatings[i] = allPoi.get(i).getPoiDetailInfo().getOverallRating();//总体评分
                    prices[i] = allPoi.get(i).getPoiDetailInfo().getPrice(); //价格
                    commentNums[i] = allPoi.get(i).getPoiDetailInfo().getCommentNum(); //评论数
                    detailUrls[i] = allPoi.get(i).getPoiDetailInfo().getDetailUrl(); //http地址
                }

                mData = getData();//将获得内容list
                mListView.setAdapter(new MyAdapter());
                return;


            }
            if (result.error == SearchResult.ERRORNO.AMBIGUOUS_KEYWORD) {
                // 当输入关键字在本市没有找到，但在其他城市找到时，返回包含该关键字信息的城市列表
                String strInfo = "在";
                for (CityInfo cityInfo : result.getSuggestCityList()) {
                    strInfo = strInfo += cityInfo.city;
                    strInfo += ",";

                }
                strInfo += "找到结果";
                Toast.makeText(getActivity(), strInfo, Toast.LENGTH_LONG)
                        .show();
            }

        }
        @Override
        public void onGetPoiDetailResult(PoiDetailSearchResult result) {
            if (result.error != SearchResult.ERRORNO.NO_ERROR) {
                Toast.makeText(getActivity(), "抱歉，未找到结果", Toast.LENGTH_SHORT)
                        .show();
            } else {

                return;
            }
        }
        @Override
        public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

        }
        //废弃
        @Override
        public void onGetPoiDetailResult(PoiDetailResult result) {
            Toast.makeText(getActivity(),result+"",Toast.LENGTH_SHORT).show();
        }
    };


    private List<Map<String, Object>> getData() {

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for(int i=0;i<datas.length;i++)
        {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("poi_name", datas[i]);
            map.put("poi_city", cities[i]);
            map.put("poi_address", addresses[i]);
            map.put("poi_types", types[i]);
            map.put("poi_overallRating",overallRatings[i]);
            map.put("poi_price",prices[i]);
            map.put("poi_comment",commentNums[i]);
            map.put("poi_detailUrl",detailUrls[i]);

            list.add(map);
        }
        return list;
    }

    private class MyPoiOverlay extends PoiOverlay {

        private PoiResult poiResult = null;

        public void setData(PoiResult poiResult) {
            this.poiResult = poiResult;
        }

        public MyPoiOverlay(BaiduMap baiduMap) {
            super(baiduMap);
            // TODO Auto-generated constructor stub
        }

        @Override
        public boolean onPoiClick(int index) {
            // TODO Auto-generated method stub
            PoiInfo poi = getPoiResult().getAllPoi().get(index);
            // if (poi.hasCaterDetails) {
            mPoiSearch.searchPoiDetail((new PoiDetailSearchOption())
                    .poiUid(poi.uid));
            return true;
        }

    }



    class MyAdapter extends BaseAdapter{
        @Override
        public int getCount(){
            return mData.size();
        }
        @Override
        public Object getItem(int position){
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            PoiDetailSearchResult result = null;
            convertView=View.inflate(getActivity(),R.layout.list_fragment_item,null);
            TextView title = (TextView) convertView.findViewById(R.id.title);
            TextView score = (TextView) convertView.findViewById(R.id.score);
            TextView money = (TextView) convertView.findViewById(R.id.money);
            TextView type = (TextView) convertView.findViewById(R.id.type);
            TextView address = (TextView) convertView.findViewById(R.id.address);
            TextView people = (TextView) convertView.findViewById(R.id.people);

            title.setText((String)mData.get(position).get("poi_name"));
            score.setText(mData.get(position).get("poi_overallRating")+"分");
            money.setText("￥"+mData.get(position).get("poi_price")+"/人");
            type.setText((String)mData.get(position).get("poi_types")+" | ");
            address.setText((String)mData.get(position).get("poi_address"));
            people.setText(mData.get(position).get("poi_comment")+"人消费");

            return convertView;
        }
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        food.setTextColor(Color.GREEN);

        food.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                food.setTextColor(Color.GREEN);
                attractions.setTextColor(Color.GRAY);
                hotel.setTextColor(Color.GRAY);
                bank.setTextColor(Color.GRAY);
                shopping.setTextColor(Color.GRAY);

                typeofsearch = "美食";
                searchInQuanzhou(typeofsearch);

                Toast.makeText(getActivity(), typeofsearch, Toast.LENGTH_SHORT).show();
            }
        });

        attractions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                food.setTextColor(Color.GRAY);
                attractions.setTextColor(Color.GREEN);
                hotel.setTextColor(Color.GRAY);
                bank.setTextColor(Color.GRAY);
                shopping.setTextColor(Color.GRAY);

                typeofsearch = "景点";
                searchInQuanzhou(typeofsearch);

                Toast.makeText(getActivity(), typeofsearch, Toast.LENGTH_SHORT).show();
            }
        });

        hotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                food.setTextColor(Color.GRAY);
                attractions.setTextColor(Color.GRAY);
                hotel.setTextColor(Color.GREEN);
                bank.setTextColor(Color.GRAY);
                shopping.setTextColor(Color.GRAY);

                typeofsearch = "酒店";
                searchInQuanzhou(typeofsearch);

                Toast.makeText(getActivity(), typeofsearch, Toast.LENGTH_SHORT).show();
            }
        });

        bank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                food.setTextColor(Color.GRAY);
                attractions.setTextColor(Color.GRAY);
                hotel.setTextColor(Color.GRAY);
                bank.setTextColor(Color.GREEN);
                shopping.setTextColor(Color.GRAY);


                typeofsearch = "银行";
                searchInQuanzhou(typeofsearch);


                Toast.makeText(getActivity(), typeofsearch, Toast.LENGTH_SHORT).show();
            }
        });

        shopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                food.setTextColor(Color.GRAY);
                attractions.setTextColor(Color.GRAY);
                hotel.setTextColor(Color.GRAY);
                bank.setTextColor(Color.GRAY);
                shopping.setTextColor(Color.GREEN);

                typeofsearch = "购物";
                searchInQuanzhou(typeofsearch);

                Toast.makeText(getActivity(), typeofsearch, Toast.LENGTH_SHORT).show();
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(),Detail.class);
                intent.putExtra("urls", (String) mData.get(position).get("poi_detailUrl"));
                startActivity(intent);
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        mPoiSearch.destroy();
        super.onDestroy();
    }

}
