<template>
    <el-image v-if="imageUrl" :src="imageUrl" :alt="alt" style="width: 35px; height: 35px;"
        @error="handleImageError"></el-image>
</template>

<script>
import axios from 'axios';

export default {
    name: 'ImageWithSession',
    props: {
        imgPath: {
            type: String,
            required: true
        },
        alt: {
            type: String,
            default: 'Image'
        }
    },
    data() {
        return {
            imageUrl: '',  // 用于存储最终图片的 URL
        };
    },
    mounted() {
        this.loadImage();
    },
    methods: {
        loadImage() {
            const sessionId = this.$store.state.user.sessionId;  // 从 Vuex 获取 sessionId
            const imagePath = '/ws/' + this.imgPath;  // 拼接图片 URL

            // 使用 axios 请求图片，附带 sessionId 请求头
            axios.get(imagePath, {
                headers: {
                    'sessionId': sessionId
                },
                responseType: 'blob'  // 获取图片作为二进制流
            })
                .then(response => {
                    // 创建一个 URL 对象，设置为 img 的 src
                    const imageObjectURL = URL.createObjectURL(response.data);
                    this.imageUrl = imageObjectURL;
                })
                .catch(error => {
                    console.error('Image loading error:', error);
                    this.imageUrl = '';  // 如果加载失败，可以设置为默认图像
                });
        },
        handleImageError() {
            console.error('Failed to load image');
            this.imageUrl = '';  // 设置一个默认的图片或处理失败
        }
    }
};
</script>
