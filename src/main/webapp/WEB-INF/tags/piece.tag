<%@ attribute name="size" required="true" rtexprvalue="true" 
 description="Size of the piece to show" %>
 <%@ attribute name="piece" required="true" rtexprvalue="true" type="com.ling1.springmvc.ocaBoard.OcaPiece"
 description="Piece to be rendered" %>
 
 image = document.getElementById('${piece.color}');
 ctx.drawImage(image,${piece.getPositionXInPixels(size)},${piece.getPositionYInPixels(size)},${size},${size});