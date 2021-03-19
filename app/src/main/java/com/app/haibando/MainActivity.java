package com.app.haibando;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.app.haibando.model.Item;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    // Khai báo bản đồ
    private GoogleMap mMap;
    // Khai báo danh sách Ổ gà
    private List<Marker> listMarker;
    // Nút bản đồ ổ gà 1
    private Button map1Btn;
    // Nút bản đồ ổ gà 2
    private Button map2Btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Màng hình được khởi tạo
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Khởi tạo màng hình và bản đồ
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapHome);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        //Hàm chạy khi ứng dung sẳn sàng
        // Gán biến quản lý Map
        mMap = googleMap;
        // Ánh xạ nút 1 và 2
        map1Btn = findViewById(R.id.map1Btn);
        map2Btn = findViewById(R.id.map2Btn);
        //Khoi tao danh sách trống
        listMarker = new ArrayList<>();
        // Bắt sự kiện nhất nút 1
        map1Btn.setOnClickListener(v -> {
            Log.e("Click","map 1");
            // Gọi hàm tải bản đồ 1
            LoadMap("point1");
        });
        // Bắt sự kiện nhất nút 2
        map2Btn.setOnClickListener(v -> {
            Log.e("Click","Long Mỹ- Vĩnh Tường");
            // Gọi hàm tải bản đồ 2
            LoadMap("point2");
        });

    }
    //Hàm tải bản đồ
    public void LoadMap(String mapId){
        // Kết nổi Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        // Lấy danh sách MapID (point1 hoặc point2)
        DatabaseReference myRef = database.getReference(mapId);
        // Đọc dữ liệu từ danh sách <mapId>
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Hàm này chạy khi dữ liệu trên firebase thay đổi
                //Xóa đánh dấu ổ gà
                for(Marker mk:listMarker){
                    mk.remove();
                }
                //Làm rỗng danh sách bản đồ
                listMarker.clear();

                //Ghi log dữ liệu thử
                Log.e("Count " ,""+dataSnapshot.getChildrenCount());
                //Lấy từng phần tử ra
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    // Ánh xạ dữ liệu
                    Item point = snapshot.getValue(Item.class);
                    // Thêm điểm đánh dấu ổ gà vào bản đồ
                    Marker mk = mMap.addMarker(new MarkerOptions().position(new LatLng(point.getLat(),point.getLng())).title("Ổ gà"));
                    // Thêm điểm đánh dấu vào danh sách điểm đánh dấu ổ gà để quản lý
                    listMarker.add(mk);
                }
                if(!listMarker.isEmpty())
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(listMarker.get(0).getPosition(),15));
                Log.e( "Value is: " , dataSnapshot.toString());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Không lấy được dữ liệu
                Log.w( "Khong lay duoc du leu", error.toException());
            }
        });

    }



}