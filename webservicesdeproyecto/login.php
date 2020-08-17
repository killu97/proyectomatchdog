<?php
include 'conexion.php';
$correo=$_POST['correo'];
$contrasena=$_POST['contrasena'];

//$correo="jackolo";
//$contrasena="kdkd8";

$sentencia=$conexion->prepare("SELECT correo, contrasena FROM users WHERE correo=? AND contrasena=?");
$sentencia->bind_param('ss',$correo,$contrasena);
$sentencia->execute();

$resultado = $sentencia->get_result();
if ($fila = $resultado->fetch_assoc()) {
         echo json_encode($fila,JSON_UNESCAPED_UNICODE);
}
$sentencia->close();
$conexion->close();
?>