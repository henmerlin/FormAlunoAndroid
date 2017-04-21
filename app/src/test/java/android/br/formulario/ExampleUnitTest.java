package android.br.formulario;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {


        //boolean b = Pattern.matches("[8,9]{1}[0-9]{7,8}", "12345697");

        //assertTrue(b);


        List<String> list = new ArrayList<>();

        list.add("Teste");
        list.add("Futebol");
        list.add("Volei");

        System.out.println(this.fraseologiaInteresses(list));
    }

    private String fraseologiaInteresses(List<String> list){
        if(list.isEmpty()){
            return ".";
        }
        StringBuilder b = new StringBuilder();
        int i = 0;
        for (String a: list) {
            if(i == list.size() - 2){
                b.append(a + " e ");
            }else if(i == list.size() - 1) {
                b.append(a + ".");
            }else{
                b.append(a + ", ");
            }
            i++;
        }
        return b.toString();
    }

}

