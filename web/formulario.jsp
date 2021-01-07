<jsp:include page="parciales/header.jsp" />

<!--
Deber�n de crear un formulario en el cual digitalizan:
nombre
edad o boleta
y un mensaje
Despu�s de ello deber� de existir un bot�n para guardar esos datos y de ah� firmar el documento
Para poder firmar el documento les solicitara en un formulario la clave privada (aqu� pueden hacer uso de los .key que les ense�e a realizar)
Una vez que se introduce se genera el PDF con la informaci�n que se introdujo + la visualizaci�n de la firma en la parte inferior del PDF.

-->

<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>   
<script type="text/javascript" src="https://rawcdn.githack.com/franz1628/validacionKeyCampo/bce0e442ee71a4cf8e5954c27b44bc88ff0a8eeb/validCampoFranz.js"></script>
<script>
    $(function(){
  //Para escribir solo letras
  $('#miCampo1').validCampoFranz(' abcdefghijklmn�opqrstuvwxyz��iou');
  //Para escribir solo numeros    
  $('#miCampo2').validCampoFranz('0123456789'); });
</script>


<div class="container">
    <div class="row mt-2">
        <div class="col">
            <div class="card">
                <div class="card-body">
                    <form action="Obtenerdatos" method="post">
                        <h2>Datos:</h2>
                        <div class="form-group">
                            <input type="text" ondrop="return false;" onpaste="return false;" ondragstart="return false" onselectstart="return false"  id="miCampo1" name="name" placeholder="Nombre" class="form-control" required="true" minlength="2" maxlength="20">
                        </div>
                        <div class="form-group">
                            <input type="number" ondrop="return false;" onpaste="return false;" ondragstart="return false" onselectstart="return false" name="old" placeholder="Edad" class="form-control" required="true" min="1" max="120">
                        </div>
                        <div class="form-group">
                            <input type="text" ondrop="return false;" onpaste="return false;" ondragstart="return false" onselectstart="return false" name="msj" placeholder="Mensaje" class="form-control" required="true" minlength="2">  
                        </div>
            
                        <button type="submit" class="btn btn-primary" >
                            Guardar datos
                        </button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="parciales/footer.jsp" />