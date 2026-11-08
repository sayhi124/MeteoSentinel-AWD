$ErrorActionPreference='Stop'
$projectRoot=Split-Path $PSScriptRoot -Parent
$file=Join-Path $projectRoot '.run/processes.jsonl'
if(Test-Path -LiteralPath $file){
 Get-Content -LiteralPath $file | ForEach-Object {
  $entry=$_|ConvertFrom-Json
  $running=Get-CimInstance Win32_Process -Filter "ProcessId=$($entry.id)" -ErrorAction SilentlyContinue
  if($running -and $running.CommandLine.Contains($entry.jar)){Stop-Process -Id $entry.id}
 }
 Remove-Item -LiteralPath $file
}
