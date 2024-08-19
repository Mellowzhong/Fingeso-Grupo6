<script setup>
import { useStore } from 'vuex';
import { computed } from 'vue';

const store = useStore();

const property = computed(() => store.getters.getProperty);
</script>

<template>
    <div class="flex flex-col flex-grow h-screen bg-gray-200 rounded-xl mx-32 my-4 p-4">
        <div class="flex flex-grow">
            <div class="bg-gray-400 w-[40rem] h-[32rem] flex-1"></div>
            <div class="flex-1 p-4 space-y-2">
                <h1 class="text-4xl">{{ property.type }}</h1>
                <section class="space-y-2">
                    <div class="flex space-x-2">
                        <div class="bg-black bg-opacity-20 flex items-center rounded-xl px-3 py-0.5">
                            <span class="material-symbols-rounded">
                            bed
                            </span>
                            {{ property.rooms }}
                        </div>
                        <div class="bg-black bg-opacity-20 flex items-center rounded-xl px-3 py-0.5">
                            Baños: {{ property.bathrooms }}
                        </div>
                        <div v-show="property.parking" class="bg-black bg-opacity-20 flex items-center rounded-xl px-3 py-0.5">
                            <span class="material-symbols-rounded">
                            directions_car
                            </span>
                        </div>
                        <div v-show="property.furnished" class="bg-black bg-opacity-20 flex items-center rounded-xl px-3 py-0.5">
                            Amoblado
                        </div>
                        <div class="bg-black bg-opacity-20 flex items-center rounded-xl px-3 py-0.5">
                            {{ property.squareMeters }} Mt²
                        </div>
                    </div>
                    <div>
                        <h2 class="text-2xl">Servicios Cercanos:</h2>
                        <p>{{ property.services }}</p>
                    </div>
                </section>
                <section>
                    <h2 class="text-2xl">Dirección:</h2>
                    <p>{{ property.direction }}</p>
                </section>
                <section>
                    <h2 class="text-2xl">Precio:</h2>
                    <p>UF {{ property.price }}</p>
                </section>
                <div class="flex">
                    <section class="flex-1">
                        <h2 class="text-2xl">Año de construcción:</h2>
                        <p>{{ property.yearConstruction }}</p>
                    </section>
                    <section class="flex-1">
                        <h2 class="text-2xl">Estado:</h2>
                        <p>{{ property.state }}</p>
                    </section>
                </div>
                <section>
                    <h2 class="text-2xl">Propietario / Corredor de inmuebles</h2>
                    <div class="flex items-center space-x-2">
                        <img class="rounded-full aspect-square object-cover" src="../../Images/Mob 2.png" width="64" height="64"
                            alt="Foto de perfil" />
                        <h3>Alonso Sanhueza</h3>
                    </div>
                </section>
                <section>
                    <h2 class="text-2xl">Descripción</h2>
                    <p>{{ property.description }}</p>
                </section>
            </div>
        </div>
        <div class="flex items-center space-x-2 mt-auto">
            <button class="bg-blue-500 text-white rounded-lg p-2">Solicitar Compra</button>
            <button class="bg-blue-500 text-white rounded-lg p-2">Solicitar Arriendo</button>
            <button class="bg-blue-500 text-white rounded-lg p-2">Solicitar agendamiento</button>
        </div>
    </div>
</template>