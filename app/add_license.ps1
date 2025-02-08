$license = Get-Content -Path "LICENSE_HEADER.txt" -Raw
Get-ChildItem -Recurse -Filter "*.kt" | ForEach-Object {
  $content = Get-Content $_.FullName
  if (-not ($content -match "MIT License")) {
    $license + "`n" + $content | Set-Content $_.FullName
    Write-Host "Added license to $($_.FullName)"
  } else {
    Write-Host "License already exists in $($_.FullName)"
  }
}