package com.algaworks.junit.blog.negocio;

import com.algaworks.junit.blog.modelo.Editor;

import java.math.BigDecimal;
import java.util.Locale;

public class EditorTestData {

    private EditorTestData(){

    }

    public static Editor.Builder umEditorNovo(){
      return  Editor.builder()
              .comNome("Lucas")
              .comEmail("lucas@gmail.com")
              .comValorPagoPorPalavra(BigDecimal.TEN)
              .comPremium(true);
    }

    public static Editor.Builder umEditorExistente(){
       return umEditorNovo().comid(1L);
    }

    public static Editor.Builder umEditorComIdInexistente(){
        return umEditorNovo().comid(99L);
    }




    }

