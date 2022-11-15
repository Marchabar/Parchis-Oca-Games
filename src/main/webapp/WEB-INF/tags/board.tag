<%@ attribute name="ocaBoard" required="false" rtexprvalue="true" type="com.ling1.springmvc.ocaBoard.OcaBoard"
 description="Ocaboard to be rendered" %>
<canvas id="canvas" width="${ocaBoard.width}" height="${ocaBoard.height}"></canvas>
<img id="source" src="${ocaBoard.background}" style="display:none">
<img id="RED-PIECE" src="resources/images/rojo.png" style="display:none">
<script>
function drawBoard(){ 
    var canvas = document.getElementById("canvas");
    var ctx = canvas.getContext("2d");
    var image = document.getElementById('source');
    ctx.drawImage(image, 0, 0, ${ocaBoard.width}, ${ocaBoard.height});     
    <jsp:doBody/>
}
window.onload =drawBoard();
</script>