package com.example.duolingoapp.bocauhoi;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.duolingoapp.R;
import com.example.duolingoapp.mokhoa_bch.OpenCourseActivity;
import java.util.List;

public class QuestionListAdapter extends RecyclerView.Adapter<QuestionListAdapter.QuestionViewHolder> {
    private Context context;
    private int layout;
    private List<QuestionList> questionList;
    private AlertDialog alertDialog;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
    public QuestionListAdapter(Context context, int layout, List<QuestionList> questionList) {
        this.context = context;
        this.layout = layout;
        this.questionList = questionList;
    }

    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new QuestionViewHolder(view);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder holder, int position) {
        QuestionList BoHT = questionList.get(position);
        holder.txtTenBo_ENG.setText(BoHT.getStt() + ". " + BoHT.getTenBo_ENG());
        holder.txtTenBo_VIE.setText(BoHT.getStt() + ". " + BoHT.getTenBo_VIE());
        Bitmap img= BitmapFactory.decodeByteArray(BoHT.getImg(),0,BoHT.getImg().length);
        holder.subjectImage.setImageBitmap(img);

        // Thêm OnClickListener cho txtUnlockClass
        holder.txtUnlockClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Inflate layout của dialog_opencourses.xml
                LayoutInflater inflater = LayoutInflater.from(context);
                View dialogView = inflater.inflate(R.layout.dialog_opencourses, null);

                // Tạo dialog
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setView(dialogView);

                // Tìm các thành phần trong layout dialog_opencourses.xml
                TextView txtCloseCourse = dialogView.findViewById(R.id.txtCloseCourse);
                Button btnOpenCourse = dialogView.findViewById(R.id.btnOpenCourse);

                // Tạo và hiển thị dialog
                alertDialog = alertDialogBuilder.create();
                alertDialog.show();

                // Gắn OnClickListener cho txtCloseCourse
                txtCloseCourse.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss(); // Đóng dialog
                    }
                });

                // Gắn OnClickListener cho btnOpenCourse
                btnOpenCourse.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, OpenCourseActivity.class);
                        context.startActivity(intent);
                        alertDialog.dismiss(); // Đóng dialog
                    }
                });
            }
        });

        // Xử lý sự kiện click
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }

    public static class QuestionViewHolder extends RecyclerView.ViewHolder {
        TextView txtTenBo_ENG, txtTenBo_VIE;
        TextView txtUnlockClass;

        ImageView subjectImage;

        public QuestionViewHolder(View itemView) {
            super(itemView);
            txtTenBo_ENG = itemView.findViewById(R.id.txtSubjectName_ENG);
            txtTenBo_VIE = itemView.findViewById(R.id.txtSubjectName_VIE);
            subjectImage = itemView.findViewById(R.id.ivSubjectImage);
            txtUnlockClass = itemView.findViewById(R.id.txtUnlockClass_ItemSubject);
        }
    }
}
