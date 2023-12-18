package com.algaworks.junit.blog.negocio;

import com.algaworks.junit.blog.armazenamento.ArmazenamentoEditor;
import com.algaworks.junit.blog.modelo.Editor;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith(MockitoExtension.class)
public class CadastroEditorComMockTest {
    Editor editor;
    @Captor
    ArgumentCaptor<Mensagem> mensagemArgumentCaptor;
    @Mock
    ArmazenamentoEditor armanezamentoEditor;
    @Mock
    GerenciadorEnvioEmail genrenciadorEnvioEmail;

    @InjectMocks
    CadastroEditor cadastroEditor;


    @BeforeEach
    void init(){
        editor = new Editor(null, "Lucas", "Lucas@email.com",BigDecimal.TEN, true);
        Mockito.when(armanezamentoEditor.salvar(Mockito.any(Editor.class)))
                .thenAnswer(invocacao -> {
                    Editor editorPassado = invocacao.getArgument(0, Editor.class);
                    editorPassado.setId(1L);
                    return editorPassado;
                });

    }
    @Test
    void Dado_um_editor_valido_Quando_criar_de_retornar_um_id_de_cadastro(){
        Editor editorSalvo = cadastroEditor.criar(editor);
        assertEquals(1L, editorSalvo.getId());
    }

    @Test
    void Dado_um_editor_valido_Quando_criar_Entao_deve_chamar_metodo_salvar_do_armazenamento(){
        cadastroEditor.criar(editor);
        Mockito.verify(armanezamentoEditor, Mockito.times(1))
                .salvar(Mockito.eq(editor));
    }

    @Test
    void Dado_um_editor_valido_Quando_cadastrar_Entao_deve_enviar_email_com_destino_ao_editor(){

        Editor editorSalvo = cadastroEditor.criar(editor);

        Mockito.verify(genrenciadorEnvioEmail).enviarEmail(mensagemArgumentCaptor.capture());

        Mensagem mensagem = mensagemArgumentCaptor.getValue();

        assertEquals(editorSalvo.getEmail(), mensagem.getDestinatario());

    }
}
