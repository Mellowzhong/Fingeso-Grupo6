<script setup>
</script>

<template>
    <main class="h-screen mx-32 my-4 p-4">
        <!-- Product Card -->
        <div class="grid grid-cols-4 gap-4">
            <figure
                class="w-64 p-4 relative bg-white rounded-lg shadow-md overflow-hidden m-2"
                v-for="item in 20"
                :key="item"
            >
                <!-- Product Image -->
                <img
                    class="w-52 h-52 object-scale-down mx-auto"
                    src=""
                    :alt="Departamento"
                />
                <!-- Product Details -->
                <figcaption class="grid">
                    <!-- Product Name -->
                    <span class="text-lg font-bold mb-2">Departamento</span>
                    <!-- Product Price -->
                    <span class="text-lg font-bold mb-2"> 1000 UF</span>
                    <!-- User Location -->
                    <span class="text-gray-600 font-bold mb-4">Chile, Providencia</span>
                </figcaption>
            </figure>
        </div>
    </main>
</template>