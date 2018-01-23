package me.sunhapper.spcharedittool.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.sunhapper.spedittool.span.GifSpanUtil;
import com.sunhapper.spedittool.view.SpEditText;
import com.sunhapper.spedittool.view.SpEditText.KeyReactListener;
import com.sunhapper.spedittool.view.SpEditText.SpData;
import java.io.IOException;
import me.sunhapper.spcharedittool.R;
import me.sunhapper.spcharedittool.util.DrawableUtil;
import me.sunhapper.spcharedittool.glide.GifTarget;
import me.sunhapper.spcharedittool.glide.PreGifDrawable;
import me.sunhapper.spcharedittool.span.GifAlignCenterSpan;
import pl.droidsonroids.gif.GifDrawable;

public class MainActivity extends AppCompatActivity {

  private static final String TAG = "MainActivity";
  private SpEditText spEditText;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    spEditText = findViewById(R.id.spEdt);
//    spEditText.setReactKeys("@#%*");
    spEditText.setKeyReactListener(new KeyReactListener() {
      @Override
      public void onKeyReact(String key) {
        switch (key) {
          case "@":
            spEditText
                .insertSpecialStr(" @sunhapper ", true, 0, new ForegroundColorSpan(Color.RED));
            break;
          case "#":
            spEditText.insertSpecialStr(" #这是一个话题# ", true, 1, new ForegroundColorSpan(Color.RED));
            break;
          case "%":
            spEditText.insertSpecialStr(" 100% ", true, 2, new ForegroundColorSpan(Color.RED));
            break;
          case "*":
            spEditText.insertSpecialStr(" ******* ", true, 3, new ForegroundColorSpan(Color.RED));
            break;
        }


      }
    });
  }

  public void insertSp(View view) {
    spEditText.insertSpecialStr(" 这是插入的字符串 ", false, 4, new ForegroundColorSpan(Color.BLUE));
  }

  public void getData(View view) {
    SpData[] spDatas = spEditText.getSpDatas();
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(spEditText.getText())
        .append("\n");
    for (SpData spData : spDatas) {
      stringBuilder.append(spData.toString())
          .append("\n");
    }
    Log.i(TAG, "getData: " + stringBuilder);
    Toast.makeText(this, stringBuilder, Toast.LENGTH_SHORT).show();
  }

  public void insertGif(View view) {
    try {
      GifDrawable gifDrawable = new GifDrawable(getResources(), R.drawable.a);
      ImageSpan imageSpan = new GifAlignCenterSpan(gifDrawable);
      spEditText.insertSpecialStr("[a]", false, "[a]", imageSpan);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void setGif(View view) {
    try {
      GifDrawable gifDrawable = new GifDrawable(getResources(), R.drawable.a);
      GifDrawable gifDrawable1 = new GifDrawable(getResources(), R.drawable.b);
      PreGifDrawable preGifDrawable = new PreGifDrawable();
      Glide.with(this)
          .asGif()
          .load(
              "http://5b0988e595225.cdn.sohucs.com/images/20170919/1ce5d4c52c24432e9304ef942b764d37.gif")
          .into(new GifTarget(preGifDrawable));
      CharSequence charSequence = DrawableUtil.getDrawableText("[a]", gifDrawable);
      CharSequence charSequence1 = DrawableUtil.getDrawableText("[b]", gifDrawable1);
      CharSequence charSequence2 = DrawableUtil.getDrawableText("[c]", preGifDrawable);
      SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
      spannableStringBuilder
          .append("表情1:").append(charSequence)
          .append("表情2:").append(charSequence1)
          .append("表情3:").append(charSequence2);
      GifSpanUtil.setText(spEditText, spannableStringBuilder);
      spEditText.setSelection(spannableStringBuilder.length());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
