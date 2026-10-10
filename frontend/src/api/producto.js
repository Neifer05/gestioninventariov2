// URL base de la API.
//  - Desarrollo: "/api"  -> Vite lo redirige a http://localhost:8080 (ver vite.config.js)
//  - Producción: se define con la variable de entorno VITE_API_URL
const BASE = import.meta.env.VITE_API_URL || "http://localhost:8080/api";

async function pedir(url, opciones = {}) {
  const res = await fetch(url, {
    headers: { "Content-Type": "application/json" },
    ...opciones,
  });

  if (!res.ok) {
    // El backend devuelve el mensaje de error en JSON
    let mensaje = `Error ${res.status}`;
    try {
      const data = await res.json();
      mensaje = data.mensaje || data.message || mensaje;
    } catch {
      /* la respuesta no traía JSON */
    }
    throw new Error(mensaje);
  }

  // 204 = No Content (DELETE) no trae cuerpo
  if (res.status === 204) return null;
  return res.json();
}

export function obtenerProductos() {
  return pedir(`${BASE}/productos`);
}

export function crearProducto(producto) {
  return pedir(`${BASE}/productos`, {
    method: "POST",
    body: JSON.stringify(producto),
  });
}

export function borrarProducto(id) {
  return pedir(`${BASE}/productos/${id}`, { method: "DELETE" });
}
