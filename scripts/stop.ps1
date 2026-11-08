$ErrorActionPreference='Stop'
$projectRoot=Split-Path $PSScriptRoot -Parent
$file=Join-Path $projectRoot '.run/processes.jsonl'
if(Test-Path -LiteralPath $file){
 Get-Content -LiteralPath $file | ForEach-Object {
  $entry=$_|ConvertFrom-Json
  $running=Get-CimInstance Win32_Process -Filter "ProcessId=$($entry.id)" -ErrorAction SilentlyContinue
  $normalizedJar=[IO.Path]::GetFullPath($entry.jar)
  if($running -and $running.CommandLine.Replace('/','\').Contains($normalizedJar)){Stop-Process -Id $entry.id}
 }
 Remove-Item -LiteralPath $file
}
