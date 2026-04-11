#version 150

#moj_import <lodestone:common_math.glsl>

uniform sampler2D MainDepthSampler;
uniform mat4 invProjMat;
uniform mat4 invViewMat;
uniform vec3 cameraPos;

in vec2 texCoord;
out vec4 fragColor;

void main() {
    vec3 worldPos = getWorldPos(MainDepthSampler, texCoord, invProjMat, invViewMat, cameraPos);

    float distance = length(worldPos - cameraPos);

    float maxDistance = 180.0;
    float brightness = 1.0 - clamp(distance / maxDistance, 0.0, 1.0);

    fragColor = vec4(brightness, brightness, brightness, 1.0);
}