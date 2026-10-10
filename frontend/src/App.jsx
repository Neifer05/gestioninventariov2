import { BrowserRouter, Routes, Route, NavLink, Navigate } from "react-router-dom";
import ProductosPage from "./pages/ProductosPage";
import ExistenciasPage from "./pages/ExistenciasPage";

export default function App() {
    return (
        <BrowserRouter>
            <nav>
                <NavLink to="/productos">Productos</NavLink>
                <NavLink to="/existencias">Existencias</NavLink>
            </nav>

            <h1>Gestión de Inventario</h1>

            <Routes>
                <Route path="/" element={<Navigate to="/productos" replace />} />
                <Route path="/productos"   element={<ProductosPage />} />
                <Route path="/existencias" element={<ExistenciasPage />} />
                <Route path="*" element={<Navigate to="/productos" replace />} />
            </Routes>
        </BrowserRouter>
    );
}
