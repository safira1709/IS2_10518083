<?php
if($_SERVER['REQUEST_METHOD']=='POST'){
$id
= $_POST['id'];
$nama = $_POST['nama'];
$nohp = $_POST['nohp'];
$angkatan = $_POST['angkatan'];
$kelas = $_POST['kelas'];
require_once('koneksi.php');
$sql = "UPDATE contact SET nama = '$nama', nohp = '$nohp', angkatan='$angkatan', kelas = '$kelas' WHERE id =
$id;";
if(mysqli_query($con,$sql)){
echo 'Berhasil Mengubah Data KM/i';
}else{
echo 'Gagal Mengubah Data KM/i';
}
mysqli_close($con);
}
?>