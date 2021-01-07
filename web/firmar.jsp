<jsp:include page="parciales/header.jsp" />

<% String nom = (String)request.getAttribute("name"); %>
<% String eda = (String)request.getAttribute("edad"); %>
<% String men = (String)request.getAttribute("mensaje"); %>


<div class="container">
    <div class="row mt-2">
        <div class="col">
            <div class="card">
                <div class="card-body">
                    <form action="clave" method="post" > <!-- enctype="multipart/form-data" es para leer el archivo pero creo que no lo usare, nota mental-->
                        <h2>Firmar pdf:</h2>
                        <div class="form-group">
      <label for="exampleInputFile">Subir archivo</label>
      <input type="file" class="form-control-file" name="archivo" aria-describedby="fileHelp">
      <input type="hidden" name="nombree" value=<%= nom%>>
      <input type="hidden" name="edadd" value=<%= eda%>>
      <input type="hidden" name="mensajee" value=<%= men%>>
      <small id="fileHelp" class="form-text text-muted">Sube la clave privada .key (se encuentra en la carpeta mencionada al inicio con el nombre de "privatekey.key")</small>
    </div>
                        <button type="submit" class="btn btn-primary" >
                            Firmar
                        </button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<!--

-->


<jsp:include page="parciales/footer.jsp" />