<?php
require_once('koneksi.php');
$id
= $_GET['id'];
$sql = "DELETE FROM contact WHERE id=$id;";
if(mysqli_query($con,$sql)){
echo 'Berhasil Menghapus data';
}else{
echo 'Gagal Menghapus data';
}
mysqli_close($con);
?>