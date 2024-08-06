<template>
    <div class="user-info-body">
        <section class="user-info-header">
            <img class="user-info-header-image" src="../../Images/Mob 2.png" width="500" height="500"
                alt="Foto de perfil" />
            <div class="user-info-header-name">
                <h2 class="user-info-header-user-name">Alonso Sanhueza</h2>
                <div class="user-info-header-user-name-star-section">
                    <input class="user-info-header-name-user-star" type="radio">
                    <input class="user-info-header-name-user-star" type="radio">
                    <input class="user-info-header-name-user-star" type="radio">
                    <input class="user-info-header-name-user-star" type="radio">
                    <input class="user-info-header-name-user-star" type="radio">
                    <span class="user-info-header-user-age"> 24 años</span>
                </div>
            </div>
        </section>
        <section class="user-info-description">
            <h2 class="user-info-description-tittle">Descripción</h2>
            <p class="user-info-description-description">Aqui iria la descripcion del corredor de inmuebles</p>
        </section>
        <section class="user-info-inmuebles">
            <h2 class="user-info-inmuebles-tittle">Historial de inmuebles:</h2>
            <div class="user-info-inmuebles-list">
                <!-- Listado de inmuebles -->
            </div>
        </section>
    </div>
</template>

<script setup>

</script>

<style>
.user-info-body {
    margin: 20px;
    display: grid;
    background-color: rgb(190, 187, 187);
    padding: 4vh;
}

.user-info-header {
    display: flex;
    align-items: center;
}

.user-info-header-image {
    border-radius: 50%;
}

.user-info-header-name {
    display: grid;
    text-align: center;
    margin-left: 10vh;
}

.user-info-header-user-name {
    font-size: 10vh;
}

.user-info-header-user-name-star-section {
    display: flex;
    justify-content: center;
}

.user-info-header-name-user-star {
    width: 60px;
    height: 100px;
}

.user-info-header-user-age {
    font-size: 5vh;
    text-align: center;
    margin-top: auto;
    margin-bottom: auto;
    margin-left: 6vh;
}

.user-info-description-tittle {
    font-size: 5vh;
}

.user-info-description-description {
    font-size: 2vh;
}

.user-info-inmuebles-tittle {
    font-size: 5vh;
}
</style>