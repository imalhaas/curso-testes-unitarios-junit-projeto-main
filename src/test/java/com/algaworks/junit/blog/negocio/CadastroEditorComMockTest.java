package com.algaworks.junit.blog.negocio;

import com.algaworks.junit.blog.armazenamento.ArmazenamentoEditor;
import com.algaworks.junit.blog.exception.EditorNaoEncontradoException;
import com.algaworks.junit.blog.exception.RegraNegocioException;
import com.algaworks.junit.blog.modelo.Editor;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith(MockitoExtension.class)
public class CadastroEditorComMockTest {
    @Spy
    Editor editor = new Editor(null, "Lucas", "Lucas@email.com",BigDecimal.TEN, true);
    @Captor
    ArgumentCaptor<Mensagem> mensagemArgumentCaptor;
    @Mock
    ArmazenamentoEditor armanezamentoEditor;
    @Mock
    GerenciadorEnvioEmail genrenciadorEnvioEmail;

    @InjectMocks
    CadastroEditor cadastroEditor;


    @Nested
   class cadastroEditorValido {

        @Spy
        Editor editor = new Editor(null, "Lucas", "Lucas@email.com",BigDecimal.TEN, true);

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
            verify(armanezamentoEditor, times(1))
                    .salvar(Mockito.eq(editor));
        }

        //capturando parametros enviados aos mocks com argument captor.
        @Test
        void Dado_um_editor_valido_Quando_cadastrar_Entao_deve_enviar_email_com_destino_ao_editor(){

            Editor editorSalvo = cadastroEditor.criar(editor);

            verify(genrenciadorEnvioEmail).enviarEmail(mensagemArgumentCaptor.capture());

            Mensagem mensagem = mensagemArgumentCaptor.getValue();

            assertEquals(editorSalvo.getEmail(), mensagem.getDestinatario());

        }

        //espionando um objeto real com mokito
        @Test
        void Dado_um_editor_valido_Quando_cadastrar_Entao_deve_verificar_o_email(){
            Editor editorSpy = Mockito.spy(editor);
            cadastroEditor.criar(editor);
            verify(editorSpy, Mockito.atLeast(1)).getEmail();
        }

        //Alterando retorno de um mock após chamadas consecutivas
        @Test
        void Dado_um_editor_com_email_existente_Quando_cadastrar_Entao_deve_lancar_exception(){
            Mockito.when(armanezamentoEditor.encontrarPorEmail("Lucas@email.com"))
                    .thenReturn(Optional.empty())
                    .thenReturn(Optional.of(editor));
            Editor editorComEmailExistente = new Editor(null, "Lucas", "Lucas@email.com",BigDecimal.TEN, true);
            cadastroEditor.criar(editor);
            assertThrows(RegraNegocioException.class, ()-> cadastroEditor.criar(editorComEmailExistente));
        }

        //Verificando ordem de chamada de métodos
        @Test
        void Dado_um_editor_valido_Quando_cadastrar_Entao_deve_enviar_email_apos_salvar(){
            cadastroEditor.criar(editor);

            InOrder inOrder = Mockito.inOrder(armanezamentoEditor, genrenciadorEnvioEmail);
            inOrder.verify(armanezamentoEditor, times(1)).salvar(editor);
            inOrder.verify(genrenciadorEnvioEmail, times(1)).enviarEmail(Mockito.any(Mensagem.class));
        }

    }

    @Nested
    class CadastroComEditorNull{

        @Test
        void dado_um_cadastro_com_editor_null_deve_lancar_exception(){
            Assertions.assertThrows(NullPointerException.class, ()-> cadastroEditor.criar(null));
            verify(armanezamentoEditor, never()).salvar(Mockito.any());
            verify(genrenciadorEnvioEmail, never()).enviarEmail(Mockito.any());
        }

        @Nested
        class EdicaoComEditorValido {
            @Spy
            Editor editor = new Editor(1L, "Alex", "alex@email.com", BigDecimal.TEN, true);

            @BeforeEach
            void init() {
                Mockito.when(armanezamentoEditor.salvar(editor)).thenAnswer(invocacao -> invocacao.getArgument(0, Editor.class));
                Mockito.when(armanezamentoEditor.encontrarPorId(1L)).thenReturn(Optional.of(editor));
            }

            @Test
            void dado_um_editor_valido_Quando_editar_Entao_deve_alterar_editor_salvo() {
                Editor editorAtualizado = new Editor(1L, "Alex Silva", "alex.silva@email.com", BigDecimal.ZERO, false);
                cadastroEditor.editar(editorAtualizado);
                verify(editor, times(1)).atualizarComDados(editorAtualizado);

                InOrder inOrder = Mockito.inOrder(editor, armanezamentoEditor);
                inOrder.verify(editor).atualizarComDados(editorAtualizado);
                inOrder.verify(armanezamentoEditor).salvar(editor);
            }
        }

        @Nested
        class EdicaoComEditorInexistente {

            Editor editor = new Editor(99L, "Lucas", "lucas@email.com", BigDecimal.TEN, true);

            @BeforeEach
            void init() {
                Mockito.when(armanezamentoEditor.encontrarPorId(99L)).thenReturn(Optional.empty());
            }

            @Test
            void Dado_um_editor_que_nao_exista_Quando_editar_Entao_deve_lancar_exception() {
                assertThrows(EditorNaoEncontradoException.class, ()-> cadastroEditor.editar(editor));
                verify(armanezamentoEditor, never()).salvar(Mockito.any(Editor.class));
            }

        }

        }
}
