/**
 * ==========================================================================
 * AGROCAMPO • DISTRIBUIDORA CAMPESINA B2B
 * Lógica de Negocio, Autenticación Dinámica por Roles y Paneles Especializados
 * SENA Centro Industrial de Mantenimiento y Manufactura (CIMM)
 * ==========================================================================
 */

const State = {
    usuarioActual: null,         // Datos del usuario con sesión activa
    rolActivo: 'VISITANTE',       // 'VISITANTE', 'COMPRADOR', 'CAMPESINO', 'ADMIN'
    productos: [],               // Catálogo completo de cosechas
    campesinos: [],              // Lista de productores campesinos
    pedidos: [],                 // Pedidos de compra
    canasta: [],                 // Items en la canasta de compra
    direccionEntrega: null,      // Dirección guardada del comprador
    categoriaActiva: 'Todas',     // Filtro de categoría en catálogo
    busquedaActiva: '',          // Texto de búsqueda
    tabLoginModal: 'comprador',  // Tab seleccionado en modal login
    subtabCampesino: 'registrar',// 'registrar' | 'mis-cosechas'
    subtabAdmin: 'comerciantes',  // 'comerciantes' | 'productos' | 'pedidos'
    slideActual: 0,              // Índice del slide del carrusel
    totalSlides: 4,              // Total de slides en el carrusel
    timerCarrusel: null          // Temporizador de rotación automática
};

// ==========================================================================
// 1. INICIALIZACIÓN DE LA APLICACIÓN
// ==========================================================================
document.addEventListener('DOMContentLoaded', async () => {
    cargarSesionGuardada();
    cargarDireccionGuardada();
    cargarCanastaGuardada();

    await Promise.all([
        cargarCampesinos(),
        cargarProductos(),
        cargarPedidos()
    ]);

    configurarEventListeners();
    actualizarHeaderSegunRol();
    iniciarRotacionCarrusel();

    // Si ya había una sesión previa, entrar a su panel; de lo contrario mostrar Inicio
    if (State.usuarioActual && State.usuarioActual.autenticado) {
        redirigirSegunRol(State.rolActivo, false);
    } else {
        irAInicio();
    }
});

// ==========================================================================
// 2. GESTIÓN DE SESIÓN Y LOCALSTORAGE
// ==========================================================================
function cargarSesionGuardada() {
    try {
        const u = localStorage.getItem('agro_usuario');
        if (u) {
            State.usuarioActual = JSON.parse(u);
            State.rolActivo = normalizarRol(State.usuarioActual.rol);
        }
    } catch (e) {
        console.warn('Error al restaurar sesión guardada:', e);
        State.usuarioActual = null;
        State.rolActivo = 'VISITANTE';
    }
}

function guardarSesion(usuario) {
    State.usuarioActual = usuario;
    State.rolActivo = normalizarRol(usuario.rol);
    localStorage.setItem('agro_usuario', JSON.stringify(usuario));
    actualizarHeaderSegunRol();
}

function cerrarSesion() {
    State.usuarioActual = null;
    State.rolActivo = 'VISITANTE';
    localStorage.removeItem('agro_usuario');
    actualizarHeaderSegunRol();
    irAInicio();
    mostrarToast('Has cerrado sesión correctamente.', 'info');
}

function normalizarRol(rolStr) {
    if (!rolStr) return 'VISITANTE';
    const r = rolStr.toUpperCase();
    if (r.includes('ADMIN')) return 'ADMIN';
    if (r.includes('CAMPESINO') || r.includes('VENDEDOR')) return 'CAMPESINO';
    if (r.includes('COMPRADOR')) return 'COMPRADOR';
    return 'VISITANTE';
}

function cargarDireccionGuardada() {
    try {
        const d = localStorage.getItem('agro_direccion');
        if (d) {
            State.direccionEntrega = JSON.parse(d);
            renderizarPreviewDireccion(State.direccionEntrega);
        }
    } catch (e) {}
}

function cargarCanastaGuardada() {
    try {
        const c = localStorage.getItem('agro_canasta');
        if (c) {
            State.canasta = JSON.parse(c);
            actualizarBadgeCanasta();
        }
    } catch (e) {}
}

function guardarCanasta() {
    localStorage.setItem('agro_canasta', JSON.stringify(State.canasta));
    actualizarBadgeCanasta();
    renderizarItemsCanasta();
}

// ==========================================================================
// 3. NAVEGACIÓN Y CAMBIO DE PANELES
// ==========================================================================
function irAInicio() {
    irAVista('inicio');
}

function irAVista(vistaId) {
    document.querySelectorAll('.view-panel').forEach(panel => panel.classList.remove('active'));
    document.querySelectorAll('.nav-menu button').forEach(btn => btn.classList.remove('active'));

    const destino = document.getElementById(`view-${vistaId}`);
    if (destino) {
        destino.classList.add('active');
    }

    const navBtn = document.querySelector(`.nav-menu button[data-view="${vistaId}"]`);
    if (navBtn) {
        navBtn.classList.add('active');
    }

    window.scrollTo({ top: 0, behavior: 'smooth' });

    // Cargas perezosas específicas
    if (vistaId === 'mercado') {
        renderizarCatalogoMercado();
    } else if (vistaId === 'inicio') {
        renderizarInicioProductosYCampesinos();
        iniciarRotacionCarrusel();
    } else if (vistaId === 'comerciante-panel') {
        actualizarDatosPanelCampesino();
        detenerRotacionCarrusel();
    } else if (vistaId === 'admin-panel') {
        actualizarDatosPanelAdmin();
        detenerRotacionCarrusel();
    } else if (vistaId === 'direccion') {
        cargarFormularioDireccion();
        detenerRotacionCarrusel();
    }
}

// ==========================================================================
// 3.1 CONTROL DEL CARRUSEL DE COSECHAS CON ROTACIÓN AUTOMÁTICA CONTINUA
// ==========================================================================
let carouselProgress = 0;
const SLIDE_DURATION_MS = 4000; // Cambia cada 4 segundos
const PROGRESS_STEP_MS = 40;

function iniciarRotacionCarrusel() {
    detenerRotacionCarrusel();
    carouselProgress = 0;
    actualizarBarraProgreso(0);

    State.timerCarrusel = setInterval(() => {
        carouselProgress += (PROGRESS_STEP_MS / SLIDE_DURATION_MS) * 100;
        if (carouselProgress >= 100) {
            carouselProgress = 0;
            moverSlideManual(1);
        }
        actualizarBarraProgreso(carouselProgress);
    }, PROGRESS_STEP_MS);
}

function detenerRotacionCarrusel() {
    if (State.timerCarrusel) {
        clearInterval(State.timerCarrusel);
        State.timerCarrusel = null;
    }
}

function actualizarBarraProgreso(pct) {
    const bar = document.getElementById('carousel-progress-bar');
    if (bar) {
        bar.style.width = Math.min(pct, 100) + '%';
    }
}

function saltarASlide(indice) {
    const slides = document.querySelectorAll('.carousel-slide');
    const dots = document.querySelectorAll('.indicator-dot');
    if (slides.length === 0) return;

    if (indice < 0) indice = slides.length - 1;
    if (indice >= slides.length) indice = 0;

    State.slideActual = indice;
    carouselProgress = 0;
    actualizarBarraProgreso(0);

    slides.forEach((s, idx) => {
        if (idx === indice) s.classList.add('active');
        else s.classList.remove('active');
    });

    dots.forEach((d, idx) => {
        if (idx === indice) d.classList.add('active');
        else d.classList.remove('active');
    });
}

function moverSlideManual(delta) {
    saltarASlide(State.slideActual + delta);
}

function filtrarMercadoPorCategoria(categoria) {
    State.categoriaActiva = categoria;
    irAVista('mercado');

    // Actualizar botones de categoría en el catálogo
    document.querySelectorAll('.pill-category').forEach(btn => {
        if (btn.getAttribute('data-categoria') === categoria) {
            btn.classList.add('active');
        } else {
            btn.classList.remove('active');
        }
    });

    mostrarToast(`🌾 Mostrando cosechas de: ${categoria}`, 'info');
}

function redirigirSegunRol(rol, notificar = true) {
    if (rol === 'ADMIN') {
        irAVista('admin-panel');
        if (notificar) mostrarToast('👑 Bienvenido al Panel de Administración Central', 'success');
    } else if (rol === 'CAMPESINO') {
        irAVista('comerciante-panel');
        if (notificar) {
            const nom = State.usuarioActual ? State.usuarioActual.nombre : 'Productor';
            mostrarToast(`👨‍🌾 ¡Bienvenido, ${nom}! Panel de Productor Rural.`, 'success');
        }
    } else if (rol === 'COMPRADOR') {
        irAVista('mercado');
        if (notificar) {
            const nom = State.usuarioActual ? State.usuarioActual.nombre : 'Comprador';
            mostrarToast(`🛒 ¡Bienvenido, ${nom}! Catálogo de compras a domicilio listo.`, 'success');
        }
    } else {
        irAInicio();
    }
}

// ==========================================================================
// 4. HEADER Y MENÚ DINÁMICO SEGÚN EL ROL
// ==========================================================================
function actualizarHeaderSegunRol() {
    const navMenu = document.getElementById('nav-menu-list');
    const actionsArea = document.getElementById('header-actions-area');
    const topIndicator = document.getElementById('top-user-indicator');

    const rol = State.rolActivo;
    const u = State.usuarioActual;

    // --- 1. Menú Central ---
    let menuHtml = '';
    if (rol === 'ADMIN') {
        menuHtml = `
            <li><button data-view="admin-panel" class="active" onclick="irAVista('admin-panel')">📊 Panel Administración</button></li>
            <li><button data-view="mercado" onclick="irAVista('mercado')">🛒 Catálogo Público</button></li>
        `;
    } else if (rol === 'CAMPESINO') {
        menuHtml = `
            <li><button data-view="comerciante-panel" class="active" onclick="irAVista('comerciante-panel')">🌾 Mi Panel de Cosechas</button></li>
            <li><button data-view="mercado" onclick="irAVista('mercado')">🛒 Catálogo en Vivo</button></li>
        `;
    } else if (rol === 'COMPRADOR') {
        menuHtml = `
            <li><button data-view="mercado" class="active" onclick="irAVista('mercado')">🌾 Catálogo de Cosechas</button></li>
            <li><button data-view="direccion" onclick="irAVista('direccion')">📍 Mi Dirección</button></li>
            <li><button data-view="inicio" onclick="irAInicio()">ℹ️ Servicios y Campesinos</button></li>
        `;
    } else {
        // Visitante
        menuHtml = `
            <li><button data-view="inicio" class="active" onclick="irAInicio()">🏠 Inicio</button></li>
            <li><button data-view="mercado" onclick="irAVista('mercado')">🌾 Catálogo de Cosechas</button></li>
            <li><button data-view="direccion" onclick="irAVista('direccion')">📍 Entrega a Domicilio</button></li>
        `;
    }
    navMenu.innerHTML = menuHtml;

    // --- 2. Acciones del Header (Derecha) ---
    let actionsHtml = '';
    if (u && u.autenticado) {
        const rolTexto = rol === 'ADMIN' ? 'Administrador' : (rol === 'CAMPESINO' ? 'Campesino Productor' : 'Comprador');
        const rolIcon = rol === 'ADMIN' ? '👑' : (rol === 'CAMPESINO' ? '👨‍🌾' : '🛒');

        actionsHtml = `
            <div class="user-logged-box">
                <div class="user-avatar-circle">${rolIcon}</div>
                <div class="user-logged-info">
                    <span class="user-logged-name">${escapeHtml(u.nombre || 'Usuario')}</span>
                    <span class="user-logged-role">${rolTexto}</span>
                </div>
                <button class="btn-logout-header" onclick="cerrarSesion()" title="Cerrar Sesión">
                    <span>🚪 Salir</span>
                </button>
            </div>
        `;
        if (rol === 'COMPRADOR' || rol === 'VISITANTE') {
            actionsHtml += `
                <button class="btn-header-cart" onclick="abrirCanasta()" title="Ver Canasta Campesina">
                    <span>🧺 Canasta</span>
                    <span class="cart-pill-badge" id="header-cart-badge">${State.canasta.length}</span>
                </button>
            `;
        }
    } else {
        actionsHtml = `
            <button class="btn-header-login" onclick="abrirModalLogin('comprador')">
                <span>🔑</span> Iniciar Sesión / Ingresar
            </button>
            <button class="btn-header-cart" onclick="abrirCanasta()" title="Ver Canasta Campesina">
                <span>🧺 Canasta</span>
                <span class="cart-pill-badge" id="header-cart-badge">${State.canasta.length}</span>
            </button>
        `;
    }
    actionsArea.innerHTML = actionsHtml;

    // --- 3. Top bar indicator ---
    if (u && u.autenticado) {
        topIndicator.innerHTML = `
            <div class="top-user-chip">
                <span>👤 Sesión: <strong>${escapeHtml(u.nombre)}</strong> (${rol})</span>
            </div>
        `;
    } else {
        topIndicator.innerHTML = `
            <div class="top-user-chip" style="cursor: pointer;" onclick="abrirModalLogin('comprador')">
                <span>🔑 Ingresar por Rol: Comprador • Campesino • Admin</span>
            </div>
        `;
    }
}

// ==========================================================================
// 5. CARGA DE DATOS DESDE LA API REST JAVA
// ==========================================================================
async function cargarCampesinos() {
    try {
        const res = await fetch('/api/campesinos');
        if (res.ok) {
            const json = await res.json();
            State.campesinos = json.datos || [];
        }
    } catch (e) {
        console.warn('Carga offline de campesinos.');
    }
}

async function cargarProductos() {
    try {
        const res = await fetch('/api/productos');
        if (res.ok) {
            const json = await res.json();
            State.productos = json.datos || [];
            actualizarKpisEnUI();
        }
    } catch (e) {
        console.warn('Carga offline de productos.');
    }
}

async function cargarPedidos() {
    try {
        const res = await fetch('/api/pedidos');
        if (res.ok) {
            const json = await res.json();
            State.pedidos = json.datos || [];
        }
    } catch (e) {
        console.warn('Carga offline de pedidos.');
    }
}

function actualizarKpisEnUI() {
    const kpiCamp = document.getElementById('stat-campesinos-home');
    if (kpiCamp && State.campesinos.length > 0) {
        kpiCamp.innerText = State.campesinos.length;
    }
    const kpiProd = document.getElementById('stat-cosechas-home');
    if (kpiProd && State.productos.length > 0) {
        kpiProd.innerText = State.productos.length;
    }
}

// ==========================================================================
// 6. RENDERIZADO DE INICIO (HOME)
// ==========================================================================
function renderizarInicioProductosYCampesinos() {
    // 1. Cosechas destacadas (primeros 4 productos)
    const featuredGrid = document.getElementById('home-featured-products-grid');
    if (featuredGrid) {
        const destacados = State.productos.slice(0, 4);
        featuredGrid.innerHTML = destacados.map(p => crearCardProductoHTML(p)).join('');
    }

    // 2. Familias campesinas
    const farmersGrid = document.getElementById('home-farmers-grid');
    if (farmersGrid) {
        farmersGrid.innerHTML = State.campesinos.map(c => `
            <div class="farmer-clean-card">
                <img src="${c.fotoUrl || 'images/don_pedro.jpg'}" alt="${escapeHtml(c.nombre)}" class="farmer-clean-avatar" onerror="this.src='images/don_pedro.jpg'">
                <div class="farmer-clean-info">
                    <h4>${escapeHtml(c.nombre)}</h4>
                    <span class="farmer-finca">🏡 ${escapeHtml(c.finca)}</span>
                    <span class="farmer-region">📍 ${escapeHtml(c.region || c.municipio || 'Boyacá')}</span>
                </div>
            </div>
        `).join('');
    }
}

// ==========================================================================
// 7. RENDERIZADO DE CATÁLOGO DEL MERCADO
// ==========================================================================
function renderizarCatalogoMercado() {
    const grid = document.getElementById('catalog-products-grid');
    if (!grid) return;

    let lista = State.productos;

    // Filtro por categoría
    if (State.categoriaActiva && State.categoriaActiva !== 'Todas') {
        lista = lista.filter(p => p.categoria === State.categoriaActiva);
    }

    // Filtro por búsqueda
    if (State.busquedaActiva && State.busquedaActiva.trim() !== '') {
        const q = State.busquedaActiva.toLowerCase();
        lista = lista.filter(p => 
            (p.nombre && p.nombre.toLowerCase().includes(q)) ||
            (p.campesinoProductor && p.campesinoProductor.toLowerCase().includes(q)) ||
            (p.region && p.region.toLowerCase().includes(q)) ||
            (p.categoria && p.categoria.toLowerCase().includes(q))
        );
    }

    if (lista.length === 0) {
        grid.innerHTML = `
            <div style="grid-column: 1 / -1; text-align: center; padding: 3rem 1rem; background: var(--c-superficie); border-radius: var(--radio-lg); border: 1.5px dashed var(--c-borde);">
                <span style="font-size: 2.5rem; display: block; margin-bottom: 0.5rem;">🌾</span>
                <h3 style="color: var(--c-bosque); margin-bottom: 0.35rem;">No se encontraron cosechas</h3>
                <p style="color: var(--c-texto-suave);">Prueba con otra palabra clave o selecciona 'Todas las Cosechas'.</p>
            </div>
        `;
        return;
    }

    grid.innerHTML = lista.map(p => crearCardProductoHTML(p)).join('');
}

function crearCardProductoHTML(p) {
    const precio = formatoCOP(p.precioMayorista || p.precioVenta || 0);
    const stock = p.stock || 0;
    const campesino = escapeHtml(p.campesinoProductor || 'Productor Asociado');
    const finca = escapeHtml(p.finca || p.region || 'Región Central');
    const foto = p.imagenUrl || 'images/papa_cosecha.jpg';

    return `
        <div class="product-card-modern">
            <div class="product-card-img-wrapper">
                <img src="${foto}" alt="${escapeHtml(p.nombre)}" class="product-card-img" onerror="this.src='images/papa_cosecha.jpg'">
                <span class="product-badge-destacado">${escapeHtml(p.etiquetaDestacada || 'Cosecha Fresca')}</span>
                <span class="product-badge-cat">${escapeHtml(p.categoria || 'Agro')}</span>
            </div>
            <div class="product-card-body">
                <span class="product-farmer-tag">👨‍🌾 ${campesino} • ${finca}</span>
                <h3 class="product-title">${escapeHtml(p.nombre)}</h3>
                <p class="product-desc">${escapeHtml(p.descripcion || 'Producto 100% fresco cosechado directamente en finca campesina.')}</p>
                
                <div class="product-price-strip">
                    <div class="price-unit-col">
                        <span class="price-unit-lbl">${escapeHtml(p.unidadMedida || 'Unidad')}</span>
                        <span class="price-unit-val">${precio}</span>
                    </div>
                    <span class="stock-pill">Disponibles: ${stock}</span>
                </div>

                <div class="product-add-action">
                    <div class="qty-stepper">
                        <button type="button" onclick="modificarCantidadCard(${p.id}, -1)">−</button>
                        <input type="text" id="qty-input-${p.id}" value="1" readonly>
                        <button type="button" onclick="modificarCantidadCard(${p.id}, 1)">+</button>
                    </div>
                    <button class="btn-add-cart-card" onclick="agregarProductoDesdeCard(${p.id})">
                        <span>🧺</span> Agregar
                    </button>
                </div>
            </div>
        </div>
    `;
}

function modificarCantidadCard(prodId, delta) {
    const input = document.getElementById(`qty-input-${prodId}`);
    if (!input) return;
    let val = parseInt(input.value) || 1;
    val += delta;
    if (val < 1) val = 1;
    if (val > 100) val = 100;
    input.value = val;
}

function agregarProductoDesdeCard(prodId) {
    const input = document.getElementById(`qty-input-${prodId}`);
    const cantidad = input ? parseInt(input.value) || 1 : 1;
    agregarACanasta(prodId, cantidad);
    if (input) input.value = 1;
}

// ==========================================================================
// 8. CANASTA DE COMPRAS CAMPESINA (COMPRADOR)
// ==========================================================================
function agregarACanasta(prodId, cantidad = 1) {
    const producto = State.productos.find(p => p.id === prodId);
    if (!producto) return;

    const existente = State.canasta.find(item => item.id === prodId);
    if (existente) {
        existente.cantidad += cantidad;
    } else {
        State.canasta.push({
            id: producto.id,
            nombre: producto.nombre,
            precio: producto.precioMayorista || producto.precioVenta || 0,
            unidadMedida: producto.unidadMedida || 'Bulto',
            imagenUrl: producto.imagenUrl || 'images/papa_cosecha.jpg',
            cantidad: cantidad,
            campesino: producto.campesinoProductor || 'Productor del Campo'
        });
    }

    guardarCanasta();
    mostrarToast(`¡${cantidad}x ${producto.nombre} añadido a tu canasta!`, 'success');
}

function eliminarDeCanasta(index) {
    if (index >= 0 && index < State.canasta.length) {
        const item = State.canasta[index];
        State.canasta.splice(index, 1);
        guardarCanasta();
        mostrarToast(`Se eliminó ${item.nombre} de la canasta.`, 'info');
    }
}

function actualizarBadgeCanasta() {
    const totalItems = State.canasta.reduce((sum, item) => sum + item.cantidad, 0);
    const badges = [
        document.getElementById('header-cart-badge'),
        document.getElementById('btn-count-canasta-banner')
    ];
    badges.forEach(b => {
        if (b) b.innerText = totalItems;
    });
}

function abrirCanasta() {
    renderizarItemsCanasta();
    const drawer = document.getElementById('cart-drawer-backdrop');
    if (drawer) drawer.classList.add('active');
}

function cerrarCanasta(e) {
    if (e && e.target !== e.currentTarget && !e.target.classList.contains('cart-drawer-close')) return;
    const drawer = document.getElementById('cart-drawer-backdrop');
    if (drawer) drawer.classList.remove('active');
}

function renderizarItemsCanasta() {
    const container = document.getElementById('cart-items-container');
    const subtotalEl = document.getElementById('cart-subtotal');
    const totalEl = document.getElementById('cart-total');
    const destIndicator = document.getElementById('cart-dest-indicator');

    if (!container) return;

    if (State.canasta.length === 0) {
        container.innerHTML = `
            <div style="text-align: center; padding: 3rem 1rem;">
                <span style="font-size: 3rem; display: block; margin-bottom: 0.5rem;">🧺</span>
                <h4 style="color: var(--c-bosque); margin-bottom: 0.25rem;">Tu canasta campesina está vacía</h4>
                <p style="font-size: 0.85rem; color: var(--c-texto-suave);">Explora el catálogo y apoya a las familias campesinas con cosechas directas de finca.</p>
            </div>
        `;
        if (subtotalEl) subtotalEl.innerText = '$0 COP';
        if (totalEl) totalEl.innerText = '$0 COP';
        return;
    }

    let subtotal = 0;
    container.innerHTML = State.canasta.map((item, idx) => {
        const itemTotal = item.precio * item.cantidad;
        subtotal += itemTotal;
        return `
            <div class="cart-item-card">
                <img src="${item.imagenUrl}" alt="${escapeHtml(item.nombre)}" class="cart-item-img" onerror="this.src='images/papa_cosecha.jpg'">
                <div class="cart-item-info">
                    <h5 class="cart-item-title">${escapeHtml(item.nombre)}</h5>
                    <span class="cart-item-sub">${escapeHtml(item.campesino)} • ${item.cantidad} x ${escapeHtml(item.unidadMedida)}</span>
                    <span class="cart-item-price">${formatoCOP(itemTotal)}</span>
                </div>
                <button class="btn-remove-item" onclick="eliminarDeCanasta(${idx})" title="Quitar">✕</button>
            </div>
        `;
    }).join('');

    if (subtotalEl) subtotalEl.innerText = formatoCOP(subtotal);
    if (totalEl) totalEl.innerText = formatoCOP(subtotal);

    if (destIndicator) {
        if (State.direccionEntrega && State.direccionEntrega.direccion) {
            destIndicator.innerHTML = `
                📍 <strong>Despachar a:</strong> ${escapeHtml(State.direccionEntrega.direccion)}, ${escapeHtml(State.direccionEntrega.ciudad || '')} (${escapeHtml(State.direccionEntrega.nombre || '')})
            `;
        } else {
            destIndicator.innerHTML = `
                ⚠️ <strong>Sin dirección de entrega:</strong> <a onclick="cerrarCanasta(); irAVista('direccion');" style="color: var(--c-terracota); font-weight: 700; text-decoration: underline; cursor: pointer;">Registrar dirección aquí</a>
            `;
        }
    }
}

async function confirmarPedidoDesdeCanasta() {
    if (State.canasta.length === 0) {
        mostrarToast('Agrega al menos un producto a la canasta.', 'error');
        return;
    }

    if (!State.direccionEntrega || !State.direccionEntrega.direccion) {
        mostrarToast('Por favor registra tu dirección de entrega antes de ordenar.', 'error');
        cerrarCanasta();
        irAVista('direccion');
        return;
    }

    const total = State.canasta.reduce((sum, item) => sum + (item.precio * item.cantidad), 0);
    const pedidoBody = {
        compradorNombre: State.direccionEntrega.nombre || (State.usuarioActual ? State.usuarioActual.nombre : 'Comprador'),
        compradorEmpresa: (State.usuarioActual && State.usuarioActual.empresa) ? State.usuarioActual.empresa : 'Hogar / Negocio',
        ciudadDestino: State.direccionEntrega.ciudad || 'Boyacá / Bogotá',
        direccionEntrega: State.direccionEntrega.direccion,
        telefonoContacto: State.direccionEntrega.telefono || '3100000000',
        valorTotal: total,
        detalles: State.canasta.map(item => ({
            productoId: item.id,
            nombreProducto: item.nombre,
            cantidad: item.cantidad,
            precioUnitario: item.precio,
            subtotal: item.precio * item.cantidad
        }))
    };

    try {
        const res = await fetch('/api/pedidos', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(pedidoBody)
        });

        // Vaciamos canasta
        State.canasta = [];
        guardarCanasta();
        cerrarCanasta();

        await cargarPedidos();

        mostrarToast('🎉 ¡Pedido confirmado! Tu cosecha será despachada a tu dirección.', 'success');
        alert(`🌾 ¡PEDIDO GENERADO CON ÉXITO!\n\nDestino de Entrega: ${pedidoBody.direccionEntrega}\nCiudad: ${pedidoBody.ciudadDestino}\nTotal a Pagar: ${formatoCOP(total)}\n\nEl camión de la distribuidora alistará las cosechas directamente de finca.`);
    } catch (e) {
        mostrarToast('Error al conectar con el servidor de pedidos.', 'error');
    }
}

// ==========================================================================
// 9. DIRECCIÓN DE ENTREGA (COMPRADOR)
// ==========================================================================
function cargarFormularioDireccion() {
    if (!State.direccionEntrega) return;
    const d = State.direccionEntrega;
    const setVal = (id, v) => { const el = document.getElementById(id); if (el) el.value = v || ''; };

    setVal('dir-nombre', d.nombre);
    setVal('dir-telefono', d.telefono);
    setVal('dir-ciudad', d.ciudad);
    setVal('dir-barrio', d.barrio);
    setVal('dir-direccion', d.direccion);
    setVal('dir-indicaciones', d.indicaciones);

    renderizarPreviewDireccion(d);
}

function guardarDireccionEntrega(e) {
    e.preventDefault();
    const d = {
        nombre: document.getElementById('dir-nombre').value.trim(),
        telefono: document.getElementById('dir-telefono').value.trim(),
        ciudad: document.getElementById('dir-ciudad').value.trim(),
        barrio: document.getElementById('dir-barrio').value.trim(),
        direccion: document.getElementById('dir-direccion').value.trim(),
        indicaciones: document.getElementById('dir-indicaciones').value.trim()
    };

    State.direccionEntrega = d;
    localStorage.setItem('agro_direccion', JSON.stringify(d));
    renderizarPreviewDireccion(d);
    mostrarToast('✅ Dirección de entrega guardada correctamente.', 'success');
}

function renderizarPreviewDireccion(d) {
    const tit = document.getElementById('preview-dir-titulo');
    const det = document.getElementById('preview-dir-detalle');
    if (tit && d && d.direccion) {
        tit.innerText = `${d.direccion} (${d.barrio || 'Sector'}, ${d.ciudad || ''})`;
        det.innerText = `Destinatario: ${d.nombre || ''} • Tel: ${d.telefono || ''} • Indicaciones: ${d.indicaciones || 'Ninguna'}`;
    }
}

// ==========================================================================
// 10. PANEL COMERCIANTE CAMPESINO (PRODUCTOR)
// ==========================================================================
function cambiarSubtabCampesino(subtab) {
    State.subtabCampesino = subtab;
    document.querySelectorAll('#view-comerciante-panel .subtab-btn').forEach(b => b.classList.remove('active'));
    document.querySelectorAll('#view-comerciante-panel .subpanel-container').forEach(p => p.classList.remove('active'));

    const btn = document.getElementById(`subtab-btn-${subtab}`);
    const panel = document.getElementById(`subpanel-campesino-${subtab}`);

    if (btn) btn.classList.add('active');
    if (panel) panel.classList.add('active');

    if (subtab === 'mis-cosechas') {
        renderizarMisCosechasCampesino();
    }
}

function actualizarDatosPanelCampesino() {
    const bannerNombre = document.getElementById('comerciante-banner-nombre');
    const bannerFinca = document.getElementById('comerciante-banner-finca');
    const quickStats = document.getElementById('comerciante-quick-stats');

    const u = State.usuarioActual;
    if (u) {
        if (bannerNombre) bannerNombre.innerText = `Bienvenido, ${u.nombre}`;
        if (bannerFinca) bannerFinca.innerText = `Finca / Origen: ${u.empresa || u.ciudad || 'Vereda Campesina'} • Código: #${u.id || '01'}`;
    }

    renderizarMisCosechasCampesino();
}

function calcularTotalLoteCampesino() {
    const stock = parseFloat(document.getElementById('camp-prod-stock').value) || 0;
    const precio = parseFloat(document.getElementById('camp-prod-precio').value) || 0;
    const totalEl = document.getElementById('camp-lote-total');
    if (totalEl) {
        totalEl.innerText = formatoCOP(stock * precio);
    }
}

async function guardarProductoCampesino(e) {
    e.preventDefault();

    const u = State.usuarioActual;
    const stock = parseInt(document.getElementById('camp-prod-stock').value) || 1;
    const precio = parseFloat(document.getElementById('camp-prod-precio').value) || 0;

    const nuevoProd = {
        nombre: document.getElementById('camp-prod-nombre').value.trim(),
        categoria: document.getElementById('camp-prod-categoria').value,
        stock: stock,
        unidadMedida: document.getElementById('camp-prod-unidad').value.trim(),
        precioMayorista: precio,
        fechaCosecha: document.getElementById('camp-prod-fecha').value.trim(),
        metodoCultivo: document.getElementById('camp-prod-metodo').value.trim() || 'Agroecológico Tradicional',
        descripcion: document.getElementById('camp-prod-desc').value.trim(),
        campesinoProductor: u ? u.nombre : 'Don Pedro Morales',
        campesinoId: u ? (u.id || 1) : 1,
        finca: u ? (u.empresa || 'Finca La Primavera') : 'Finca La Primavera',
        region: u ? (u.ciudad || 'Tuta, Boyacá') : 'Tuta, Boyacá',
        imagenUrl: 'images/papa_cosecha.jpg',
        disponible: true
    };

    try {
        const res = await fetch('/api/productos', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(nuevoProd)
        });

        if (res.ok) {
            const data = await res.json();
            if (data.datos) {
                State.productos.unshift(data.datos);
            } else {
                nuevoProd.id = Date.now();
                State.productos.unshift(nuevoProd);
            }
        } else {
            nuevoProd.id = Date.now();
            State.productos.unshift(nuevoProd);
        }

        mostrarToast('🌱 ¡Cosecha registrada con éxito en AgroCampo!', 'success');
        document.getElementById('form-comerciante-producto').reset();
        calcularTotalLoteCampesino();

        cambiarSubtabCampesino('mis-cosechas');
    } catch (err) {
        nuevoProd.id = Date.now();
        State.productos.unshift(nuevoProd);
        mostrarToast('Cosecha registrada localmente.', 'success');
        cambiarSubtabCampesino('mis-cosechas');
    }
}

function renderizarMisCosechasCampesino() {
    const tbody = document.getElementById('tbody-mis-cosechas-campesino');
    const countBadge = document.getElementById('count-mis-cosechas');
    if (!tbody) return;

    const u = State.usuarioActual;
    const campesinoId = u ? u.id : 4;
    const nombreCamp = u ? u.nombre.toLowerCase() : 'don pedro';

    // Filtrar cosechas que pertenezcan a este campesino
    let misProds = State.productos.filter(p => 
        (p.campesinoId === campesinoId) ||
        (p.campesinoProductor && p.campesinoProductor.toLowerCase().includes(nombreCamp))
    );

    // Si no tiene productos asignados por ID o nombre, mostrar las primeras cosechas de muestra
    if (misProds.length === 0) {
        misProds = State.productos.slice(0, 3);
    }

    if (countBadge) countBadge.innerText = misProds.length;

    tbody.innerHTML = misProds.map(p => {
        const totalLote = (p.stock || 0) * (p.precioMayorista || p.precioVenta || 0);
        return `
            <tr>
                <td><strong>${escapeHtml(p.nombre)}</strong><br><small style="color: var(--c-texto-muted);">${escapeHtml(p.fechaCosecha || 'Cosecha Reciente')}</small></td>
                <td><span class="table-badge-status status-activo">${escapeHtml(p.categoria || 'Agro')}</span></td>
                <td>${escapeHtml(p.unidadMedida || 'Bulto')}</td>
                <td><strong>${p.stock || 0}</strong></td>
                <td>${formatoCOP(p.precioMayorista || 0)}</td>
                <td><strong style="color: var(--c-bosque);">${formatoCOP(totalLote)}</strong></td>
                <td><span class="table-badge-status status-activo">✓ En Venta</span></td>
            </tr>
        `;
    }).join('');
}

// ==========================================================================
// 11. PANEL ADMINISTRADOR CENTRAL
// ==========================================================================
function cambiarSubtabAdmin(subtab) {
    State.subtabAdmin = subtab;
    document.querySelectorAll('#view-admin-panel .subtab-btn').forEach(b => b.classList.remove('active'));
    document.querySelectorAll('#view-admin-panel .subpanel-container').forEach(p => p.classList.remove('active'));

    const btn = document.getElementById(`admin-tab-btn-${subtab}`);
    const panel = document.getElementById(`subpanel-admin-${subtab}`);

    if (btn) btn.classList.add('active');
    if (panel) panel.classList.add('active');
}

function actualizarDatosPanelAdmin() {
    // 1. KPIs
    const kpiCamp = document.getElementById('admin-kpi-campesinos');
    const kpiProd = document.getElementById('admin-kpi-productos');
    const kpiStock = document.getElementById('admin-kpi-stock');
    const kpiPed = document.getElementById('admin-kpi-pedidos');

    if (kpiCamp) kpiCamp.innerText = State.campesinos.length;
    if (kpiProd) kpiProd.innerText = State.productos.length;

    const totalStock = State.productos.reduce((sum, p) => sum + (p.stock || 0), 0);
    if (kpiStock) kpiStock.innerText = totalStock.toLocaleString('es-CO');

    if (kpiPed) kpiPed.innerText = State.pedidos.length;

    // 2. Tabla Comerciantes
    const tbCamp = document.getElementById('tbody-admin-campesinos');
    if (tbCamp) {
        tbCamp.innerHTML = State.campesinos.map(c => `
            <tr>
                <td>#${c.id}</td>
                <td><strong>${escapeHtml(c.nombre)}</strong></td>
                <td>${escapeHtml(c.finca)}</td>
                <td>${escapeHtml(c.region || c.municipio || 'Boyacá')}</td>
                <td>${escapeHtml(c.especialidadCultivo || 'Cosechas Variadas')}</td>
                <td>${escapeHtml(c.telefono || '+57 311 000 0000')}</td>
                <td>⭐ ${c.calificacion || '5.0'}</td>
            </tr>
        `).join('');
    }

    // 3. Tabla Productos
    const tbProd = document.getElementById('tbody-admin-productos');
    if (tbProd) {
        tbProd.innerHTML = State.productos.map(p => `
            <tr>
                <td>#${p.id}</td>
                <td><strong>${escapeHtml(p.nombre)}</strong></td>
                <td>${escapeHtml(p.categoria)}</td>
                <td>${escapeHtml(p.campesinoProductor || 'Productor')}</td>
                <td><strong>${p.stock || 0}</strong></td>
                <td>${formatoCOP(p.precioMayorista || 0)}</td>
                <td>${escapeHtml(p.unidadMedida || 'Bulto')}</td>
                <td><span class="table-badge-status status-activo">Disponible</span></td>
            </tr>
        `).join('');
    }

    // 4. Tabla Pedidos
    const tbPed = document.getElementById('tbody-admin-pedidos');
    if (tbPed) {
        if (State.pedidos.length === 0) {
            tbPed.innerHTML = `<tr><td colspan="7" style="text-align: center; color: var(--c-texto-muted);">No hay pedidos registrados aún.</td></tr>`;
        } else {
            tbPed.innerHTML = State.pedidos.map(ped => `
                <tr>
                    <td><strong>#PED-${ped.id || '01'}</strong></td>
                    <td>${escapeHtml(ped.compradorNombre || 'Comprador')}</td>
                    <td>${escapeHtml(ped.compradorEmpresa || 'Hogar')}</td>
                    <td>${escapeHtml(ped.ciudadDestino || 'Bogotá')}</td>
                    <td>${escapeHtml(ped.direccionEntrega || 'Dirección registrada')}</td>
                    <td><strong style="color: var(--c-bosque);">${formatoCOP(ped.valorTotal || 0)}</strong></td>
                    <td><span class="table-badge-status status-pendiente">En Despacho</span></td>
                </tr>
            `).join('');
        }
    }
}

// ==========================================================================
// 12. MODAL DEDICADO DE INICIO DE SESIÓN Y ACCESO DIRECTO POR ROL
// ==========================================================================
function loginDirectoRol(rol) {
    let usr = null;
    if (rol === 'ADMIN') {
        usr = {
            id: 7,
            nombre: 'Administrador Central SENA',
            email: 'admin@delcampo.co',
            rol: 'ADMINISTRADOR',
            empresa: 'Distribuidora Regional Boyacá (CIMM)',
            autenticado: true
        };
    } else if (rol === 'CAMPESINO') {
        usr = {
            id: 4,
            nombre: 'Don Pedro Morales',
            email: 'donpedro@finca.com',
            rol: 'VENDEDOR_CAMPESINO',
            empresa: 'Finca La Primavera (Tuta, Boyacá)',
            autenticado: true
        };
    } else if (rol === 'COMPRADOR') {
        usr = {
            id: 1,
            nombre: 'Don Fernando Arciniegas',
            email: 'comprador@tienda.com',
            rol: 'COMPRADOR',
            empresa: 'Supermercado La Canasta',
            autenticado: true
        };
    }

    if (usr) {
        guardarSesion(usr);
        cerrarModalLogin();
        redirigirSegunRol(State.rolActivo, true);
    }
}

function alternarFormularioManual() {
    const acc = document.getElementById('login-manual-accordion');
    const txt = document.getElementById('txt-toggle-manual');
    if (!acc) return;
    const estaVisible = acc.style.display !== 'none';
    acc.style.display = estaVisible ? 'none' : 'block';
    if (txt) {
        txt.innerText = estaVisible 
            ? '⚙️ O ingresar con correo y contraseña manual / Crear cuenta ▼' 
            : '▲ Ocultar formulario manual';
    }
    if (!estaVisible) {
        seleccionarTabModalLogin('comprador');
    }
}

function abrirModalLogin(tab = 'comprador') {
    seleccionarTabModalLogin(tab);
    const modal = document.getElementById('modal-login-backdrop');
    if (modal) modal.classList.add('active');
}

function cerrarModalLogin(e) {
    if (e && e.target !== e.currentTarget && !e.target.classList.contains('modal-close-btn')) return;
    const modal = document.getElementById('modal-login-backdrop');
    if (modal) modal.classList.remove('active');
}

function seleccionarTabModalLogin(tab) {
    State.tabLoginModal = tab;
    document.querySelectorAll('.tab-login-btn').forEach(b => b.classList.remove('active'));

    const btn = document.getElementById(`tab-login-${tab}`);
    if (btn) btn.classList.add('active');

    const secLogin = document.getElementById('modal-section-login-form');
    const secReg = document.getElementById('modal-section-registro-form');
    const demoArea = document.getElementById('modal-demo-accounts-area');

    if (tab === 'registro') {
        if (secLogin) secLogin.style.display = 'none';
        if (secReg) secReg.style.display = 'block';
        if (demoArea) demoArea.style.display = 'none';
        return;
    }

    if (secLogin) secLogin.style.display = 'block';
    if (secReg) secReg.style.display = 'none';
    if (demoArea) {
        demoArea.style.display = 'block';
        renderizarDemos1Clic(tab);
    }
}

function renderizarDemos1Clic(tab) {
    const area = document.getElementById('modal-demo-accounts-area');
    if (!area) return;

    let cuentas = [];
    if (tab === 'comprador') {
        cuentas = [
            { nombre: 'Don Fernando Arciniegas', empresa: 'Supermercado La Canasta', email: 'comprador@tienda.com', pass: '123456', rol: 'COMPRADOR' },
            { nombre: 'Chef Carlos Morales', empresa: 'Restaurante El Fogón', email: 'restaurante@fogon.co', pass: '123456', rol: 'COMPRADOR' }
        ];
    } else if (tab === 'vendedor') {
        cuentas = [
            { nombre: 'Don Pedro Morales', empresa: 'Finca La Primavera (Tuta, Boyacá)', email: 'donpedro@finca.com', pass: '123456', rol: 'VENDEDOR_CAMPESINO' },
            { nombre: 'Doña Rosa Elena Patiño', empresa: 'Parcela El Manantial (Aquitania)', email: 'donarosa@finca.com', pass: '123456', rol: 'VENDEDOR_CAMPESINO' },
            { nombre: 'Don Gonzalo Arango', empresa: 'Finca La Serranía (Pitalito, Huila)', email: 'gonzalo@finca.com', pass: '123456', rol: 'VENDEDOR_CAMPESINO' }
        ];
    } else if (tab === 'admin') {
        cuentas = [
            { nombre: 'Administrador Central SENA', empresa: 'Centro CIMM Sogamoso', email: 'admin@delcampo.co', pass: 'admin123', rol: 'ADMINISTRADOR' }
        ];
    }

    area.innerHTML = `
        <span class="demo-title">⚡ Cuentas de Acceso Rápido (1 Clic):</span>
        <div class="demo-chips-list">
            ${cuentas.map(c => `
                <div class="demo-account-chip" onclick="autoLoginDemo('${c.email}', '${c.pass}', '${c.rol}', '${c.nombre}', '${c.empresa}')">
                    <div>
                        <strong>${escapeHtml(c.nombre)}</strong>
                        <div style="font-size: 0.72rem; color: var(--c-texto-suave);">${escapeHtml(c.empresa)}</div>
                    </div>
                    <em>${c.email} →</em>
                </div>
            `).join('')}
        </div>
    `;
}

function autoLoginDemo(email, pass, rol, nombre, empresa) {
    const inputEmail = document.getElementById('modal-login-email');
    const inputPass = document.getElementById('modal-login-password');
    if (inputEmail) inputEmail.value = email;
    if (inputPass) inputPass.value = pass;

    // Login inmediato sin esperas
    const usr = {
        email: email,
        nombre: nombre,
        rol: rol,
        empresa: empresa,
        autenticado: true
    };
    guardarSesion(usr);
    cerrarModalLogin();
    redirigirSegunRol(State.rolActivo, true);
}

async function ejecutarLogin(e) {
    e.preventDefault();
    const email = document.getElementById('modal-login-email').value.trim();
    const password = document.getElementById('modal-login-password').value.trim();

    try {
        const res = await fetch('/api/auth/login', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ email, password })
        });

        if (res.ok) {
            const data = await res.json();
            const usr = data.datos || {};
            usr.autenticado = true;
            guardarSesion(usr);
            cerrarModalLogin();
            redirigirSegunRol(State.rolActivo, true);
        } else {
            // Verificación con cuentas demo locales si la API responde 401
            mostrarToast('Credenciales incorrectas. Prueba con una cuenta demo de 1 clic.', 'error');
        }
    } catch (err) {
        mostrarToast('Error de conexión al autenticar. Usa una cuenta demo de 1 clic.', 'error');
    }
}

async function ejecutarRegistroComprador(e) {
    e.preventDefault();
    const nombre = document.getElementById('reg-nombre').value.trim();
    const empresa = document.getElementById('reg-empresa').value.trim();
    const email = document.getElementById('reg-email').value.trim();
    const password = document.getElementById('reg-password').value.trim();
    const telefono = document.getElementById('reg-telefono').value.trim();
    const ciudad = document.getElementById('reg-ciudad').value.trim();

    const regData = {
        nombre,
        empresa,
        email,
        password,
        telefono,
        ciudad,
        rol: 'COMPRADOR'
    };

    try {
        const res = await fetch('/api/auth/registro', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(regData)
        });

        const usr = {
            email,
            nombre,
            empresa,
            telefono,
            ciudad,
            rol: 'COMPRADOR',
            autenticado: true
        };

        guardarSesion(usr);
        cerrarModalLogin();
        mostrarToast('🌱 ¡Cuenta creada con éxito! Bienvenido a AgroCampo.', 'success');
        redirigirSegunRol('COMPRADOR', false);
    } catch (err) {
        mostrarToast('Error al registrar usuario.', 'error');
    }
}

// ==========================================================================
// 13. EVENT LISTENERS GENERALES
// ==========================================================================
function configurarEventListeners() {
    // Buscador del mercado
    const searchInput = document.getElementById('input-buscar-mercado');
    if (searchInput) {
        searchInput.addEventListener('input', (e) => {
            State.busquedaActiva = e.target.value;
            renderizarCatalogoMercado();
        });
    }

    // Filtros de categoría
    document.querySelectorAll('.pill-category').forEach(btn => {
        btn.addEventListener('click', (e) => {
            document.querySelectorAll('.pill-category').forEach(b => b.classList.remove('active'));
            e.currentTarget.classList.add('active');
            State.categoriaActiva = e.currentTarget.getAttribute('data-categoria') || 'Todas';
            renderizarCatalogoMercado();
        });
    });
}

// ==========================================================================
// 14. UTILIDADES Y TOAST
// ==========================================================================
function formatoCOP(val) {
    return '$' + Number(val || 0).toLocaleString('es-CO') + ' COP';
}

function escapeHtml(str) {
    if (!str) return '';
    return String(str)
        .replace(/&/g, '&amp;')
        .replace(/</g, '&lt;')
        .replace(/>/g, '&gt;')
        .replace(/"/g, '&quot;')
        .replace(/'/g, '&#039;');
}

function mostrarToast(mensaje, tipo = 'info') {
    const container = document.getElementById('toast-container');
    if (!container) return;

    const toast = document.createElement('div');
    toast.className = `toast-alert ${tipo}`;
    toast.innerHTML = `<span>${mensaje}</span>`;

    container.appendChild(toast);
    setTimeout(() => {
        toast.style.opacity = '0';
        toast.style.transform = 'translateX(20px)';
        setTimeout(() => toast.remove(), 250);
    }, 3500);
}
