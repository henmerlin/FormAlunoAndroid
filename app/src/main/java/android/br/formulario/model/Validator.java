package android.br.formulario.model;

import android.widget.EditText;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by G0042204 on 27/03/2017.
 */

public class Validator {

    public static EditText isValid(EditText e) throws Exception {
        if(e.getText().toString().isEmpty()){
            e.setError("Campo não preenchido");
            throw new Exception("Campo não preenchido");
        }
        return e;
    }

    public static EditText isValidCelular(EditText e)throws Exception {
        if(!Pattern.matches("[8,9]{1}[0-9]{7,8}", e.getText())){
            e.setError("Formato incorreto");
            throw new Exception("Campo não preenchido");

        }
        e = isValid(e);
        return e;
    }

    public static EditText isValidEmail(EditText e)throws Exception {
        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(e.getText()).matches()){
            e.setError("Formato incorreto");
            throw new Exception("Campo não preenchido");
        }
        e = isValid(e);
        return e;
    }


}
