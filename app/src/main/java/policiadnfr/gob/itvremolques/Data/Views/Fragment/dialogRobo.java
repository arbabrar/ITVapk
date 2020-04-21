package policiadnfr.gob.itvremolques.Data.Views.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import policiadnfr.gob.itvremolques.R;


public class dialogRobo {

   public dialogRobo (Context context){
       final Dialog dialog=new Dialog(context);
       dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
       dialog.setCancelable(false);
       //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
       dialog.setContentView(R.layout.alert_diprove);
       Button cancelar=dialog.findViewById(R.id.btncerrarobo);
       cancelar.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               dialog.dismiss();
           }
       });

       dialog.show();


   }

}
