#version 150

#moj_import <lodestone:common_math.glsl>

uniform sampler2D DiffuseSampler;
uniform sampler2D MainDepthSampler;

uniform mat4 invProjMat;
uniform mat4 invViewMat;
uniform vec3 cameraPos;
uniform float GameTime;

uniform float Intensity;
uniform float Speed;
uniform float Frequency;
uniform float MinDistance;
uniform float MaxDistance;

in vec2 texCoord;
out vec4 fragColor;

void main() {
    vec3 worldPos = getWorldPos(MainDepthSampler, texCoord, invProjMat, invViewMat, cameraPos);
    float distance = length(worldPos - cameraPos);

    float depthFactor = smoothstep(MinDistance, MaxDistance, distance);

    float time = GameTime * Speed;

    // maybe in the future look into adding some kind of randomness for wave direction? so that it changes overtime or some shi
    float waveX = sin(texCoord.y * Frequency + time) 
                + sin(texCoord.x * Frequency * 0.5 - time * 0.5) 
                + sin((texCoord.x + texCoord.y) * Frequency * 0.8 + time * 1.2);
    float waveY = cos(texCoord.x * Frequency + time * 0.8) 
                + cos(texCoord.y * Frequency * 0.6 - time * 0.4) 
                + cos((texCoord.x - texCoord.y) * Frequency * 0.9 + time * 1.1);

    vec2 offset = vec2(0.0, waveY) * 0.33 * Intensity * depthFactor;

    vec2 finalTexCoord = texCoord + offset;
    finalTexCoord = clamp(finalTexCoord, 0.0, 1.0);

    fragColor = texture(DiffuseSampler, finalTexCoord);
}