<?php
if($_SERVER['REQUEST_METHOD']=='POST'){
$nama = $_POST['nama'];
$nohp = $_POST['nohp'];
$angkatan = $_POST['angkatan'];
$kelas = $_POST['kelas'];
$sql = "INSERT INTO contact (nama,nohp,angkatan,kelas) VALUES ('$nama','$nohp','$angkatan','$kelas')";
require_once('koneksi.php');
if(mysqli_query($con,$sql)){
echo 'Berhasil Menambahkan Data KM /i';
}else{
echo 'Gagal Menambahkan Data KM/i';
}
mysqli_close($con);
}
?>