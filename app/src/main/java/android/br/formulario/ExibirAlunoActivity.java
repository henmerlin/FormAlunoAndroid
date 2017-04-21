package android.br.formulario;

import android.br.formulario.model.Aluno;
import android.br.formulario.model.Util;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.List;

public class ExibirAlunoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exibir_aluno);
    }

    @Override
    protected void onStart() {
        super.onStart();

        TextView t = (TextView) findViewById(R.id.resultado);
        Aluno a = (Aluno) getIntent().getSerializableExtra("Aluno");
        t.setText(this.gerarStringCadastro(a));

    }

    /**
     * Luis da Unidade Hauer, e-mail luisluisluis@gmail.com e celular 987654321, interesse nas seguintes atividades: esportes e leitura.
     *
     * @param a
     * @return
     */
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
}
