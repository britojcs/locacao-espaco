# Sistema de Locação de Espaço


## Backup
- Backup todos os dias as 03:00
- Manter os dois últimos backups

```console
0 3 * * * /opt/backups/backup-script.sh
```

```console
#!/bin/sh
find /opt/backups -type f -mtime +2 -exec rm {} +
/usr/bin/mariadb-dump -u root -p'@MariaDB' villaimperial > /opt/backups/bak-`date +%Y-%m-%d_%H-%M`.sql
```
