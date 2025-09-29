# X-Road Demo

## Getting Started

### Step 1: Navigate to the Docker Folder

```bash
docker compose up -d
# To stop the containers
docker compose down
```

### Step 2: Restore Configuration

Run the restore script:

```bash
restore.bat
```

Then start the containers again:

```bash
docker compose up -d
```

---

## Credentials

Use the following credentials for all X-Road instances:

- **Username:** `xrd`
- **Password:** `secret`

**PIN for Certificates:** `7IG/*!v21Rl_`

---

## Start Backend and Frontend

Navigate to the root folder and run:

```bash
startBackendsandFrontend.bat
```

---

## Keycloak Credentials

- **Username:** `ad`
- **Password:** `ad`

## Demo Student Credentials

- **Username:** `sorin`
- **Password:** `1`

## Notes

Make sure Docker is running and all required ports are available before starting the containers.
