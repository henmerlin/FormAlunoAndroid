package android.br.formulario.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by G0042204 on 24/03/2017.
 */

class AlunoSingleton {
    private static final AlunoSingleton ourInstance = new AlunoSingleton();

    static AlunoSingleton getInstance() {
        return ourInstance;
    }

    private List<Aluno> alunos;

    private AlunoSingleton() {
        alunos = new ArrayList<>();
    }

    public void adicionar(Aluno e){
        alunos.add(e);
    }

    public List<Aluno> listar(){
        return alunos;
    }
}
