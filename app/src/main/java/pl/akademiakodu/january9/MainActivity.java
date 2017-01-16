package pl.akademiakodu.january9;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.text)
    TextView text;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // ta instrukcja pokazuje gdzie butterknife ma nasluchiwac czyli ze np w tej klasie
        ButterKnife.bind(this);

        text.setText("Siema butterknife");

        showCustomDialog();
//        showDialog();
//        showProgressDialog();

        // wywolanie AsyncTaska z ProgressDialog

//        progressDialog = new ProgressDialog(this);
//        new AsyncFake().execute();
    }

    @OnClick(R.id.butter)
    public void onClickOk(){
        Toast.makeText(this, "Ktoś kliknął przycisk", Toast.LENGTH_SHORT).show();
    }

    // Poniżej wywolanie AsyncTaska z ProgressDialog

    private class AsyncFake extends AsyncTask <Void, String, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            for (int i = 0; i<=5; i++){
                publishProgress("Coś robię: "+i);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            progressDialog.show();
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Rozpoczynam pracę!");
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressDialog.setMessage("Zakończyłem pracę");
            progressDialog.dismiss();
        }

        @Override
        protected void onProgressUpdate(String... values) {
            progressDialog.setMessage(values[0]);
        }
    }

    private void showDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("Testowe okno");
        dialogBuilder.setMessage("To jest nasze testowe okno");
        // builder umożliwia dynamiczne przypisywanie kolejnych wartości po kropce przyklad  poniżej
        // konwertowanie Buildera na dialog
        // wywolanie okna metoda create, przyciski musimy stworzyc przed metoda create
        dialogBuilder.setCancelable(false);// blokuje wejscie do aplikacji bez uzycia przyciskow
        // klikniecie poza oknem nie zamyka go - wymuszamy skorzystanie z okna
        dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialogBuilder.setNegativeButton("NIE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog dialog = dialogBuilder.create();
        // pamiętać o wyświetleniu za pomocą metody show()
        dialog.show();

        // Z builderem
        // równoważny do powyższego zapis dzieki zastosowaniu buildera - uproszczenie wszytsko w jednej linii po kropce
        AlertDialog alertDialog = new AlertDialog.Builder(this).setTitle("test").setMessage("test1").create();
    }

        public void showProgressDialog(){
            ProgressDialog progress = new ProgressDialog(this);
            progress.setTitle("Pobieranie pliku");
            progress.setMessage("Pobieram plik 1/20");
            progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progress.setIndeterminate(true);
            progress.setCancelable(false);
            progress.show();
        }


    private void showCustomDialog(){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.setTitle("Witaj świecie");
        dialog.setCancelable(false);

        // pamiętać żeby dać nazwę dialogu przed findViewById żeby szukało w dialogu do którego przypisaliśmy dany layout
        // inaczej wywali błąd no chyba że jako główny będzie ustawiony ten sam layout ale dialog i tak nie będzie działał wtedy
        Button button1 = (Button) dialog.findViewById(R.id.button1);
        final EditText edit = (EditText) dialog.findViewById(R.id.edit);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!edit.getText().toString().equals("")){
                    Toast.makeText(MainActivity.this, "Twoje imię to: "+edit.getText().toString(), Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                } else {
                    Toast.makeText(MainActivity.this, "Uzupełni pole", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.show();
    }
}

