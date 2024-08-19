<script setup>
</script>

<template>
    <div class="flex flex-col flex-grow h-screen bg-gray-200 rounded-xl mx-32 my-4 p-4">
        <div class="flex flex-grow">
            <div class="bg-gray-400 w-[40rem] h-[32rem] flex-1"></div>
            <div class="flex-1 p-4 space-y-2">
                <h1 class="text-4xl">Titulo de inmueble</h1>
                <div class="flex space-x-2">
                    <div class="bg-black bg-opacity-20 flex items-center rounded-xl px-3 py-0.5">
                        <span class="material-symbols-rounded">
                        bed
                        </span>
                        2
                    </div>
                    <div class="bg-black bg-opacity-20 flex items-center rounded-xl px-3 py-0.5">
                        Baños: 2
                    </div>
                    <div class="bg-black bg-opacity-20 flex items-center rounded-xl px-3 py-0.5">
                        Metro
                    </div>
                    <div class="bg-black bg-opacity-20 flex items-center rounded-xl px-3 py-0.5">
                        Supermercado
                    </div>
                    <div class="bg-black bg-opacity-20 flex items-center rounded-xl px-3 py-0.5">
                        Hospital
                    </div>
                </div>
                <section>
                    <h2 class="text-2xl">Dirección:</h2>
                    <p>Carlos Antunez 2229, Providencia</p>
                </section>
                <section>
                    <h2 class="text-2xl">Precio:</h2>
                    <p>UF 10000</p>
                </section>
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
                    <p>La Lorem ipsum dolor sit amet consectetur adipisicing elit. Suscipit eaque esse voluptatibus voluptates iure cum? Nihil, non dolore, modi debitis assumenda, corporis nostrum at repellat nobis vero quam minima pariatur! Lorem ipsum dolor sit amet consectetur, adipisicing elit. Ducimus veritatis optio voluptatum quam esse delectus reiciendis facere fugiat, nostrum consectetur quas veniam, pariatur deleniti architecto aliquam ipsum itaque cum obcaecati.</p>
                </section>
            </div>
        </div>
        <div class="flex space-x-2 mt-auto">
            <button class="bg-blue-500 text-white rounded-lg p-2">Solicitar Compra</button>
            <button class="bg-blue-500 text-white rounded-lg p-2">Solicitar Arriendo</button>
            <button class="bg-blue-500 text-white rounded-lg p-2">Solicitar agendamiento</button>
        </div>
    </div>
</template>