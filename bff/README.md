# Musigame BFF
## A new way of enjoying music

### Description
Ce projet est le Back-End For Front-End (BFF) de Musigame. Il s'agit d'une API REST en Spring Boot qui permet de mettre à disposition les différentes routes nécessaires à l'application front.
Le BFF fait appel à l'API de [Genius](./bff/README.md) pour récupérer les chansons https://docs.genius.com/#/getting-started-h1

### Installation
Pour lancer le projet, assurez-vous d'avoir Java 21 installé sur votre machine ainsi que Gradle.

Vous allez devoir créer un fichier `firebase_key.json` dans le dossier `src/main/resources/configuration` et y ajouter les lignes suivantes :
```json
{
  "type": "service_account",
  "project_id": "musigame-rpbb",
  "private_key_id": "2186141113bbe544b23f2740124400339a02e873",
  "private_key": "-----BEGIN PRIVATE KEY-----\nMIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCxfV4HU0dYYuGk\nn8hWgtwp7NrTGUeTtvahOFX3GWaW3Ozlij1hG2xeZWaA2+6SRuKTSCxXAivlUuwj\nDVM0nXlnqYnFJ7EUC53lgVns3ZfOi3A2u8tnu2mu2uHvgAqJWmu7D2z0yy/9ryQo\nMI54u1G+0hF3b/xiy2DKlLhyg7TjhFo2HZJ5ClU0F8ADHjHx4kIDvVeEkTIbnJhj\nuKkAC9PJE5NONwycQWca513IQusDwJmHF2PdBHiTj8d5hzEN0Hz6STYQNlUFHpNa\nXCxso2aPP07OMsKrX4DcfciNxJivI01TjV36jpDWm0bLqlW0IZLr06moO9+VqFcx\nlmJ5mqCxAgMBAAECggEAAODOKYqdsIACvkoFF3p7xOBVX9SA8GfoFfmdoUSYp5ft\nuyABOD0GvvvQ80PDTSl5HwHtKDwMRAzyOqUq+3ho/JyP4DAXVfJNxHAspfmdsemq\nMzcgJUH93p3aHtF7G91c6ZbEsvxbXy2x59n/pnTEzYUkDXt6G/IGJ1JvjC9/Il4H\n6ISNpy7OT2vRpwU8NdnEDykHkIZRb9F80kVmCIjYNXeIx5r04b9QkfwWfCGqvlrn\nmpeosnFboMMSz+4BJx+hJPal5ynEJv+1skcKiKviF9rD/6walWtBDYrKHo2pQuSV\n84AXk8ibK3nsW1tuiVEdAkCy6UkGTV+eQzNEWjuq3QKBgQDgkpccjk0b3mzp1R5H\nYpGsAqhMZMFVZsMZjz/7PPpqXZ8WmLjOLf1qVt7OZahpfDVOT5HkcBR+odvLsA55\n0gkmANrY3KTCwDbm6LjhDzeZW5cPkMlrfNkXrY2HQnqzj3D15hvzDX7GcM/25gnb\nSt1KJRuXR6BKwu/gNrok6ycRTQKBgQDKVAMRqNjcyD4F3+0uMWGhtTf/1LQP6sss\nhCl8quMIdVRRLOsCdsuIeIKQZXG/SqJrZFD2wlvi9BwkDLmWkKA+n5chJmh3BN2m\niFijB9ME30ppIhefAnC4sL2ZQGiikIVD0a1cBfvkYvUXOa375IKYn3lfkJ8QqPBp\ns5VXBKpa9QKBgAOwxQT2HbRIinepRe8cYpJ2FLf0q+ywXKJt5TpGvULEORoQ9nCE\n9vYpaU3xA7yACww3aIldgii2ljsZsJM17WqPdwk7TwXYTM6somUob9x4Udoyoey/\nR34JHxtK72NGBzAzUOBEp4GxVumSzDSrXUmbpIFu7uZrn6+KlrKGnsS9AoGALDGb\nlgDmlZsbiRb3bK6Wn8bAm53vUsa7aFbZ5QRCoPdX2n9QOfKO1JlWE4pFBT6dKY34\n7V1YysL9XpZ090FR7zvWvAPyr4SdkaGYksvDuP24+CvdMvtKed0JHO9nq5KbuUuU\nbVRVR/ahiFgt+6ZXLFUxIsGpLO9py/zQ8vfu6f0CgYBdsKhJHLuDhQ/XSBYguE13\n+vxT7c062e05l3fjPi+B4xTj22AeWklhmgQ+0aE2CJaVIVsfVBPJRYkGMH4zaqNO\n298kvd4XmuardcztV7gyYIf7dOWariKWbUpNbmglReQB1SdtA6a0UUjlTS1ivl6h\nKYHjQHIVqORqsua8F0RGOQ==\n-----END PRIVATE KEY-----\n",
  "client_email": "firebase-adminsdk-6yb46@musigame-rpbb.iam.gserviceaccount.com",
  "client_id": "101397047618126952727",
  "auth_uri": "https://accounts.google.com/o/oauth2/auth",
  "token_uri": "https://oauth2.googleapis.com/token",
  "auth_provider_x509_cert_url": "https://www.googleapis.com/oauth2/v1/certs",
  "client_x509_cert_url": "https://www.googleapis.com/robot/v1/metadata/x509/firebase-adminsdk-6yb46%40musigame-rpbb.iam.gserviceaccount.com",
  "universe_domain": "googleapis.com"
}
```

Ensuite, vous devez créer un fichier `firebase_key.json` dans le dossier `src/test/resources/configuration` et y ajouter les lignes suivantes :
```json
{
  "type": "service_account",
  "project_id": "musigame-acceptance",
  "private_key_id": "04c960fde58256d76752f961acf4eb84daecadbf",
  "private_key": "-----BEGIN PRIVATE KEY-----\nMIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQC7ZcCfZMzmULEt\nV3DdW58yL4Jv8ixDhK1J+gkWg0hKFyXCdTn+PRNVtrsVVAa3sP5V9mDctaBqfniP\nVqx7NxRsKop8vnO+xQ63x+6IPMXgPkIM/kMPoqekMktcuHJccHXpuk3Uvg6h5dtq\nEIktYH+jKtd4MRTMzKB9+nTqqs6+fnw2tnJQnF5wFzOWOC3543XncrVI4Wkr/dkM\nsGr+PVron1VxS2nHyph/9vCKNBq8zcZFBhku8zFwUjhdiv2jASuxXWbO88TH1avP\nj/0wgD680aL5pDhgLhlThnKdvq+y/uzDgFnOHfhCLrLPd5YT4EpnAu2CwdDH8ska\nTw/5SzYNAgMBAAECggEAHM5FT7sU7ApXUdrxFHWzAOngqQiW2UIZyRdQMk+32vu3\n6C5Q3c3RnQlbtKmX5sCkN96JgSPCJtyN96qhFMegsgfQBeDYtehgnMt01ShSWmf3\nizvCXzLnhgvdHcGUgrUeyvDCagNVCHnmwmUMURdWR2ScfnM8+UYzHWpUcoYTBaD0\nSq5CtDArCtApAXG1gGVSqDgGXT5vJ9s3RcspthdZKXhrPvXMQAxAYDr1nzT/8Mbe\nwoureVKX9a40B5R2SYmD3MQUH7HIt6zoMfkPm1816wABXb80XQc4XW17wwEn3zi4\nXk/MxWJUQsyUau3zFXYM1hWPVIx4DnlnKNLn3Qs0mQKBgQDpPpnamedbjHMI6TUH\noSRW3ex26EabKi/+YK6P189kbi1dmn/Tz6UC/Hp4Kx7bNYk3jRrI8Scqu/33eE2P\nDbVyjIwGEiScc+0XT5HlNbN3GFqA20TrrUOOdYu5cU8dr2E3hSDTDaH0sisovBkE\nj915DTAiEKMim8uKjxYoVN63GwKBgQDNrhkZ9sYbkJCAoQzvTT1KTMxDoXyN6SUk\nFjfX95gDXeSOzUPA62CUbx9qVvx3vsw34A8k0AQAZTExP0mVDpEP8aPEJ6S1h8Ip\nFjIy58VgqvkHpUuTvqrGHH8j5DEV5H8sWUE1M0SF8rFGfgoZSAKOXI7OpRnwODZZ\nK3fotftR9wKBgHb5/dUqaH+kdxF2lBxeMIE3FuXYuhtwO6cQfjrVpO7f3LhAlS/g\nXZXWYEpoBIdhGauCiMUzbf04g+X2Menk3keeNdbq7k34PFqFtjArYm4/t/q+3rsx\nWKKUhz+C8w57ZaXCfOnrrE97itRujnxrPlf/SCw4PF7tBbiaIH1WPX09AoGAFAHW\ncT8fADlsxQZlgrWpu5uC1yVTP16pfTwoDHsKL3GntPhkmdSLnYS9spNplaBaryi9\ncGBHs14kjdzRJI25MKrZuk/85qehDGwbT/ZZrdCTztmWY3XNmN3WxEADQlTqxf6c\nqDcneDwmYGwMQW9OyFhaj+Fhh4uAReZDkMB7BPsCgYEAmXnmSnTvQnpANYUzzP1s\nIqbd/1//HWwxwML9luIipz5+bdmnClv6UdX5vY1kXP2AzDfcOsL8+pPY8GWJ8u9b\nazH1AJ8gF2sTaC5iY7GkJStEtJw+O/1cp+wxkCuVrG9rRXnRTAszD32uMK73ad1j\nbzlidcnjbuQrl3xRE0j3UAc=\n-----END PRIVATE KEY-----\n",
  "client_email": "firebase-adminsdk-g0ny0@musigame-acceptance.iam.gserviceaccount.com",
  "client_id": "114103735120969865179",
  "auth_uri": "https://accounts.google.com/o/oauth2/auth",
  "token_uri": "https://oauth2.googleapis.com/token",
  "auth_provider_x509_cert_url": "https://www.googleapis.com/oauth2/v1/certs",
  "client_x509_cert_url": "https://www.googleapis.com/robot/v1/metadata/x509/firebase-adminsdk-g0ny0%40musigame-acceptance.iam.gserviceaccount.com",
  "universe_domain": "googleapis.com"
}
```

Enfin, vous pouvez lancer le projet avec les commandes suivantes :

```shell
cd bff
gradle build
./gradlew run
```
