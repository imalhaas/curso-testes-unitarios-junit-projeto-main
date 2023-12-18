package com.algaworks.junit.blog.negocio;

import com.algaworks.junit.blog.armazenamento.ArmazenamentoEditor;
import com.algaworks.junit.blog.modelo.Editor;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.math.BigDecimal;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class CadastroEditorComMockTest {

    CadastroEditor cadastroEditor;
    Editor editor;

    @BeforeEach
    void init(){
        editor = new Editor(null, "Lucas", "Lucas@email.com",BigDecimal.TEN, true);

        ArmazenamentoEditor armanezamentoEditor = Mockito.mock(ArmazenamentoEditor.class);
        Mockito.when(armanezamentoEditor.salvar(editor))
                .thenReturn(new Editor(1L, "Lucas", "Lucas@email.com",BigDecimal.TEN, true));
        GerenciadorEnvioEmail genrenciadorEnvioEmail = Mockito.mock(GerenciadorEnvioEmail.class);

        cadastroEditor = new CadastroEditor(armanezamentoEditor, genrenciadorEnvioEmail);
    }

    @Test
    void Dado_um_editor_valido_Quando_criar_de_retornar_um_id_de_cadastro(){
        Editor editorSalvo = cadastroEditor.criar(editor);
        Assertions.assertEquals(1L, editorSalvo.getId());

    }
}
