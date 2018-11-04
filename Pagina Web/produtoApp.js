/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(function () {
     carregar();
   
    $('#id').addClass('desabilitado');
    $('input').focus(function (){
        $('#message').hide();
    })
   
    $('#btnCancelar').click(function () {
        $('#message').hide();
        $('form').each(function () {
            this.reset();
        });
        $('#nome').focus();
    });
    
    $('#btnNovo').click(function () {
        $('#message').hide();
        $('form').each(function () {
            this.reset();
        });
        $('#nome').focus();
    });
    
    $('#btnGravar').click(function () {
        
        var temErros = false;

        $('.has-error').removeClass('has-error');
        var nome = $('#nome').val();
        if (!nome) {
            $('#nome-group').addClass('has-error');
            temErros = true;
        }
        var comentario = $('#comentario').val();
        if (!comentario) {
            $('#comentario-group').addClass('has-error');
            temErros = true;
        }
        var altura = $('#altura').val();
        if (!altura) {
            $('#altura-group').addClass('has-error');
            temErros = true;
        }
        var largura = $('#largura').val();
        if (!largura) {
            $('#largura-group').addClass('has-error');
            temErros = true;
        }
        
        var profundidade = $('#profundidade').val();
        if (!profundidade) {
            $('#profundidade-group').addClass('has-error');
            temErros = true;
        }

        if (temErros) {
            $('#message')
                    .html("<strong>Erro!</strong> Observe os campos indicados com erro!")
                    .show();
        } else {
            $.post('produto', $('form').serialize(), function (data) {
                $('#message').html("<strong>Sucesso!</strong> Registro " + data.id + " foi gravado com sucesso!").show();
                carregar();
                $('form').each(function () {
                    this.reset();
                });
            });
        }
    });

    
});

function carregar() {
        
        $.getJSON('produto').success(function (registros) {
            
            window.templateTr = window.templateTr || $('#divTable table tbody').html();

            var trHtml = window.templateTr;
            var respHtml = "";
            registros.forEach(function (item) {
                respHtml += trHtml.replace(/\{\{id\}\}/g, item.id)
                        .replace(/\{\{nome\}\}/g, item.nome)
                        .replace(/\{\{comentario\}\}/g, item.comentario)
                        .replace(/\{\{altura\}\}/g, item.altura)
                        .replace(/\{\{largura\}\}/g, item.largura)
                        .replace(/\{\{profundidade\}\}/g, item.profundidade);
                        


            });
            $('#divTable table tbody').html(respHtml);
        });
    }

function editar(id) {
    debugger;
    $.getJSON("produto?id=" + id).success(function (data) {
        $("#id").val(data.id);
        $("#nome").val(data.nome);
        $("#comentario").val(data.comentario);
        $("#altura").val(data.altura);
        $("#largura").val(data.largura);
        $("#profundidade").val(data.profundidade);
        debugger;
        
    });
}

function excluir(id) {
    if (confirm("Deseja realmente excluir o registro?")) {
        $.ajax("produto?id=" + id, {
            type: "DELETE"
        }).success(function () {
            $('#message')
                    .html("<strong>Sucesso!</strong> Registro excluido com sucesso!")
                    .show();
            carregar();
        });
    }
}