package android.br.formulario;

import android.br.formulario.model.Aluno;
import android.br.formulario.model.Util;
import android.br.formulario.model.Validator;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class AlunoActivity extends AppCompatActivity {

    private CheckBox manual;

    private Button pronto;

    private TextView resultado;

    private EditText celular;

    private EditText nome;

    private EditText email;

    private EditText matricula;

    private Button sendSMS;

    private Button sendEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aluno);
        pronto = (Button) findViewById(R.id.cadastrar);
        pronto.setEnabled(false);
        manual = (CheckBox) findViewById(R.id.manual);
        resultado = (TextView) findViewById(R.id.resultado);
        celular = (EditText) findViewById(R.id.celular);
        sendSMS = (Button) findViewById(R.id.sendSMS);
        sendEmail = (Button) findViewById(R.id.sendEmail);

        sendEmail.setVisibility(View.INVISIBLE);
        sendSMS.setVisibility(View.INVISIBLE);

        pronto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent i = new Intent(AlunoActivity.this, ExibirAlunoActivity.class);
                    //i.putExtra("Aluno", AlunoActivity.this.getCampos());
                    //startActivity(i);
                    Aluno a = AlunoActivity.this.getCampos();
                    AlunoActivity.this.clearFocus();
                    resultado.setText(AlunoActivity.this.gerarStringCadastro(a));
                    sendEmail.setVisibility(View.VISIBLE);
                    sendSMS.setVisibility(View.VISIBLE);
                }catch (Exception e){
                    AlunoActivity.this.message(e.getMessage());
                    sendEmail.setVisibility(View.INVISIBLE);
                    sendSMS.setVisibility(View.INVISIBLE);
                }
            }
        });

        manual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AlunoActivity.this.manual.isChecked()){
                    AlunoActivity.this.pronto.setEnabled(true);
                }else{
                    resultado.setText("");
                    AlunoActivity.this.pronto.setEnabled(false);
                }
            }
        });

        celular.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(s.toString().contains("[8,9]{1}[0-9]{7,8}")){
                    celular.setError("Somente números");
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!Pattern.matches("[0-9]{8,9}", s)){
                    celular.setError("Número incompleto");
                    return;
                }

                if(!Pattern.matches("[8,9]{1}[0-9]{7,8}", s)){
                    celular.setError("Formato incorreto");
                    return;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlunoActivity.this.sendMail();
            }
        });

        sendSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlunoActivity.this.sendSMS();
            }
        });


    }

    private void sendMail() {
        String addresses[] = {"luis.trevisan@up.edu.br"};
        Intent intent = new Intent(Intent.ACTION_SENDTO); //apenas e-mail
        intent.setData(Uri.parse("mailto:"));//apenas e-mail
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, "Resumo Formulário de " + nome.getText().toString());
        intent.putExtra(Intent.EXTRA_TEXT, this.resultado.getText());
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private void sendSMS() {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("smsto:987901382")); // This
        intent.putExtra("sms_body", this.resultado.getText());
        if (intent.resolveActivity(getPackageManager()) != null)
        {
            startActivity(intent);
        }
    }

    private String gerarStringCadastro(Aluno a) {
        StringBuilder b = new StringBuilder();
        b.append(a.getNome() + " da Unidade " + a.getUnidade() + ", ");
        b.append("e-mail " + a.getEmail() + " e ");
        b.append("celular " + a.getCelular() + ",");
        b.append("interessado(a) nas atividades: " + this.fraseologiaInteresses(a.getInteresses()));
        return b.toString();
    }

    private String fraseologiaInteresses(List<String> list) {
        return Util.listToPortuguese(list);
    }



    private Aluno getCampos() throws Exception {

        Aluno a = new Aluno();

        nome = (EditText) findViewById(R.id.nome);
        celular = (EditText) findViewById(R.id.celular);
        email = (EditText) findViewById(R.id.email);
        matricula = (EditText) findViewById(R.id.matricula);


        Validator.isValid(nome);
        Validator.isValidCelular(celular);
        Validator.isValidEmail(email);
        Validator.isValid(matricula);

        a.setNome(nome.getText().toString());
        a.setCelular(celular.getText().toString());
        a.setEmail(email.getText().toString());
        a.setMatricula(matricula.getText().toString());


        RadioGroup rg = (RadioGroup) findViewById(R.id.unidade);

        try {
            RadioButton unidade =  (RadioButton) findViewById(rg.getCheckedRadioButtonId());
            a.setUnidade(unidade.getText().toString());
        }catch (Exception e){
            throw new Exception("Unidade não preenchida!");
        }

        List<CheckBox> checkBoxes = new ArrayList<>();

        checkBoxes.add((CheckBox) findViewById(R.id.esportes));
        checkBoxes.add((CheckBox) findViewById(R.id.cinema));
        checkBoxes.add((CheckBox) findViewById(R.id.teatro));
        checkBoxes.add((CheckBox) findViewById(R.id.viagens));
        checkBoxes.add((CheckBox) findViewById(R.id.leitura));


        List<String> lista = this.getCamposInteresses(checkBoxes);
        a.setInteresses(lista);

        if(lista.isEmpty()){
            throw new Exception("Interesses não selecionados!");
        }

        return a;
    }

    private List<String> getCamposInteresses(List<CheckBox> checkBoxes){

        List<String> lst = new ArrayList<String>();

        for (CheckBox a: checkBoxes) {
            if(a.isChecked()){
                lst.add(a.getText().toString());
            }
        }
        return lst;
    }

    private void message(String text){
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    private void clearFocus(){
        nome.clearFocus();
        celular.clearFocus();
        email.clearFocus();
        matricula.clearFocus();
    }


}
